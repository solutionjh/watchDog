/**
 *
 */
package com.nice.datafileanomalydetection.predict.service.modelloader;

import com.nice.datafileanomalydetection.util.MapBackedClassLoader;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component("anomalyDetectionModelLoader")
public class AnomalyDetectionModelLoader {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static Hashtable<String, InputStream> modelBases = new Hashtable<String, InputStream>();
    public static MapBackedClassLoader mapBackedClassLoader = new MapBackedClassLoader(Thread.currentThread().getContextClassLoader());    // refs #953 패키지별 launcher가 사용되도록 static 제거(2018-10-18, psh)

    /**
     * InputStream 객체를 byte[] 형태로 변환
     * @param input
     * @return
     * @throws IOException
     * @throws Exception
     */
    private static byte[] convertInpuStreamToByteArray (InputStream input) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = input.read(buffer)) != - 1) {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }

    public static void unzip (ZipInputStream zis, File targetDir, boolean fileNameToLowerCase) throws IOException {
        ZipEntry zentry = null;

        while ((zentry = zis.getNextEntry()) != null) {
            String fileNameToUnzip = zentry.getName();
            if (fileNameToLowerCase) { // fileName toLowerCase
                fileNameToUnzip = fileNameToUnzip.toLowerCase();
            }

            File targetFile = new File(targetDir, fileNameToUnzip);

            if (zentry.isDirectory()) {// Directory 인 경우
                FileUtils.forceMkdir(targetFile); // 디렉토리 생성
            } else { // File 인 경우
                // parent Directory 생성
                FileUtils.forceMkdir(targetFile.getParentFile());
                unzipEntry(zis, targetFile);
            }
        }
    }

    /**
     * Zip 파일의 한 개 엔트리의 압축을 푼다.
     * 	- refs #633 Tensorflow모형 압축파일을 풀기 위해 추가 (2017-05-22, psh)
     * @param zis - Zip Input Stream
     * @param targetFile - 압축 풀린 파일의 경로
     * @return
     * @throws IOException
     */
    protected static File unzipEntry (ZipInputStream zis, File targetFile) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(targetFile);

            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = zis.read(buffer)) != - 1) {
                fos.write(buffer, 0, len);
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
        return targetFile;
    }

    public void anomalyDetectionModelLoad (String projectFile) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
//		if ( modelBases.containsKey(projectFile) == Boolean.FALSE )
//		{
        File initialFile = new File(projectFile);
        InputStream modelStream = new FileInputStream(initialFile);

        logger.info("................................ : Model load from {} start...", projectFile);
        modelClassLoad(modelStream);
        logger.info("................................ : Model load end...");

        modelBases.put(projectFile, modelStream);
//		}
    }

    private void modelClassLoad (InputStream input) throws IOException {

        JarInputStream jarStream = new JarInputStream(input);
        JarEntry entry = null;
        byte[] buf = new byte[1024];
        int len = 0;
        while ((entry = jarStream.getNextJarEntry()) != null) {
            if (! entry.isDirectory() && ! entry.getName().endsWith(".java")) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                while ((len = jarStream.read(buf)) >= 0) {
                    out.write(buf, 0, len);
                }
//                    	logger.info("=====> entry.getName() [{}]. ", entry.getName());
                mapBackedClassLoader.addResource(entry.getName(), out.toByteArray());
            }
        }
        Thread.currentThread().setContextClassLoader(mapBackedClassLoader);

        return;
    }

    public String getAnomalyDetectionModelClassName (String projectFile) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

        String mainClassName = "";

        List<String> res = new ArrayList<String>();

        File initialFile = new File(projectFile);
        InputStream modelStream = new FileInputStream(initialFile);
        JarInputStream jis = new JarInputStream(modelStream);
        JarEntry entry = null;
        while ((entry = jis.getNextJarEntry()) != null) {
            if (! entry.isDirectory()) {
                if (entry.getName().endsWith(".class") && ! entry.getName().endsWith("package-info.class")) {
                    res.add(convertPathToName(entry.getName()));
                }
            }
        }

        for (String className : res) {
            if ("".equals(mainClassName)) {
                mainClassName = className;
            } else {
                if (className.length() < mainClassName.length()) {
                    mainClassName = className;
                }
            }
        }

        logger.info("Anomaly Detection Model Class Name is [{}]. ", mainClassName);

        return mainClassName;

    }

    private String convertPathToName (String name) {
        String convertedName = convertPathToClassName(name);
        convertedName = convertedName.replaceAll("\\$",
                ".");
        return convertedName;
    }

    private String convertPathToClassName (String name) {
        String convertedName = name.replace(".class",
                "");
        convertedName = convertedName.replace("/",
                ".");
        return convertedName;
    }


}