package com.nice.datafileanomalydetection.predict.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nice.datafileanomalydetection.predict.model.TrainFromLogInfo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TrainCommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.04
     *
     * @param args
     */
    public static void initCloud (String[] args) {
        logger.info("BFMAIN ARGS CLIENT [{}]");
    }

    /**
     * export POJO
     * 외부제공용 메소드 수정, kjh, 2020.05.04
     *
     * @param destination
     * @return
     * @throws IOException
     */
    public static URI exportPOJOModel (URI destination) throws IOException {
        File destDir = new File(new File(destination.getPath()).getParent());
        logger.info("temp directory [{}]", destDir);

        if (! destDir.exists()) {
            destDir.mkdirs();
        } else {
            // 컴파일된 파일이 들어갈 폴더 정리
            FileUtils.cleanDirectory(destDir);
        }

        File destFile = new File(destination);
        FileOutputStream fos = new FileOutputStream(destFile);
        return destination;
    }

    /**
     * loadJavaFile
     * 외부제공용 메소드 수정, kjh, 2020.05.04
     *
     * @param javaFileFolder
     * @throws IOException
     */
    public static void loadAndModifyJavaFile (String javaFileFolder) throws IOException {
        logger.info("javafilefolder [{}]", javaFileFolder);
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.04
     *
     * @param javaFileFolder
     * @param scales
     * @param recMSEMap
     * @param itemRecMSEMap
     * @throws IOException
     */
    public static void loadAndModifyJavaFileOnline (String javaFileFolder, List<Double> scales, Map<String, Double> recMSEMap, Map<String, List<Double>> itemRecMSEMap) throws IOException {
        logger.info("javafilefolder [{}]", javaFileFolder);
    }

    /**
     * modify_java_model_file(java_lines, mining_type)
     * 외부제공용 메소드 수정, kjh, 2020.05.04
     */
    public static List<String> modifyJavaModelFile (List<String> javaLines, String miningType) {
        return javaLines;

    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.04
     *
     * @param POJOFileURI
     * @param javaHome
     * @param genModelPath
     * @throws IOException
     * @throws InterruptedException
     */
    public static void compilePOJO (URI POJOFileURI, String javaHome, String genModelPath) throws IOException, InterruptedException {
        String appRootDir = new File(".").getCanonicalPath().replace('\\', '/');
        logger.info("AppRootDir [{}]", appRootDir);
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.04
     *
     * @param POJOFileURI
     * @param projectFileName
     * @param javaHome
     * @throws IOException
     * @throws InterruptedException
     */
    public static void compressModelClass (URI POJOFileURI, String projectFileName, String javaHome) throws IOException, InterruptedException {

        // 폴더 jar 파일 형식으로 압축. 리눅스에서는 jar c
        Runtime rt = Runtime.getRuntime();
        File POJOFile = new File(POJOFileURI);

        logger.info("POJO FILE [{}] NAME [{}]", POJOFile, POJOFile.getName());
        // .java 파일 삭제
        POJOFile.delete();
    }


    /**
     * refs #1283 json 에서 헤더 정보와 타입정보를 가져오는 메소드 구현
     * refs #1364 JSon File의 레이아웃이 변하는 것을 대비하여 Parsing 관련 인자 변수처리, kjh, 2020-11-19
     *
     * @param trainFromLogInfo (KeyItems, TargetKeys, LogFile)
     * @param jsonEncoding
     * @param jsonItemList
     * @param jsonItemCode
     * @param jsonItemType
     * @return
     * @throws IOException
     */
    public static Map<String, List<String>> getHeaderAndTypeFromJson (TrainFromLogInfo trainFromLogInfo, String jsonEncoding, String jsonItemList, String jsonItemCode, String jsonItemType) throws IOException {
        String jsonLine;

        InputStream jsonFlatFile = new FileInputStream(new File(trainFromLogInfo.getLogFile()));
        BufferedReader br = new BufferedReader(new InputStreamReader(jsonFlatFile, jsonEncoding));

        JsonParser parser = new JsonParser();

        List<String> itemCodeList = new ArrayList<String>();
        List<String> itemTypeList = new ArrayList<String>();

        String[] keyItems = trainFromLogInfo.getKeyItems().split(",", - 1);
        String[] targetKeys = trainFromLogInfo.getTargetKeys().split(",", - 1);
        while ((jsonLine = br.readLine()) != null) {
            JsonElement element = parser.parse(jsonLine);
            boolean isKeyMatched = true;
            for (int i = 0; i < keyItems.length; i++) {
                if (! targetKeys[i].equals(element.getAsJsonObject().get(keyItems[i]).getAsString())) {
                    isKeyMatched = false;
                    break;
                }
            }

            if (isKeyMatched) {
                JsonArray allItems = element.getAsJsonObject().get(jsonItemList).getAsJsonArray();
                for (int i = 0; i < allItems.size(); i++) {
                    JsonObject itemJson = allItems.get(i).getAsJsonObject();
                    String itemCode = itemJson.get(jsonItemCode).getAsString();
                    String itemType = itemJson.get(jsonItemType).getAsString();

                    itemCodeList.add(itemCode);
                    itemTypeList.add(itemType);
                }

                break;
            }
        }

        Map<String, List<String>> headerAndTypeMap = new HashMap<>();
        headerAndTypeMap.put("headerList", itemCodeList);
        headerAndTypeMap.put("typeList", itemTypeList);

        return headerAndTypeMap;
    }

    public static List<String> getStrDatesBetween (String fromDate, String toDate) {
        // 반복 실행을 위한 date array 생성
        LocalDate startDate = LocalDate.parse(fromDate);
        LocalDate endDate = LocalDate.parse(toDate);
        List<String> totalDates = new ArrayList<>();
        while (! startDate.isAfter(endDate)) {
            totalDates.add(startDate.toString());
            startDate = startDate.plusDays(1);
        }

        return totalDates;
    }

    public static String mergeCSV (List<String> csvFileStrList) throws IOException {
        List<File> csvFileList = csvFileStrList.stream().map(fileStr -> new File(fileStr)).collect(Collectors.toList());
        logger.info("CSVFILELIST SIZE [{}]", csvFileList.size());
        String[] headers = null;
        File firstFile = csvFileList.get(0);
        logger.info("First File is [{}]", firstFile.toString());
        Scanner scanner = new Scanner(firstFile);

        if (scanner.hasNextLine()) {
            headers = scanner.nextLine().split(",");
            logger.info("Readed header is [{}]", String.join(",", headers));
        }

        scanner.close();

        Iterator<File> iterFiles = csvFileList.iterator();
        String inputFileEncoding = PredictCommonUtils.findFileEncoding(firstFile.toString());
        FileOutputStream mergedFileOs = new FileOutputStream(firstFile, true);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(mergedFileOs, inputFileEncoding));

        while (iterFiles.hasNext()) {
            File nextFile = iterFiles.next();
            if (Files.isSameFile(firstFile.toPath(), nextFile.toPath())) {
                continue;
            }

            logger.info("Next File [{}]", nextFile);
            inputFileEncoding = PredictCommonUtils.findFileEncoding(nextFile.toString());

            FileInputStream nextFileIs = new FileInputStream(nextFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(nextFileIs, inputFileEncoding));
            //BufferedReader reader = new BufferedReader(new FileReader(nextFile));

            String line = null;
            String[] firstLine = null;
            if ((line = br.readLine()) != null) {
                firstLine = line.split(",");
                logger.info("FirstLine header [{}]", String.join(",", firstLine));
            }
            if (! Arrays.equals(headers, firstLine)) {
                throw new RuntimeException("Header mis-match between CSV files: '" + firstFile + "' and '" + nextFile.getAbsolutePath());
            }
            while ((line = br.readLine()) != null) {
                //logger.info("Line [{}]", line);
                writer.write(line);
                writer.newLine();
            }

            br.close();
        }
        writer.close();

        return inputFileEncoding;
    }

    public static void testDirectoryMethod () throws IOException {
        // path test
        logger.info("From File class Path is [{}]", new File(".").getCanonicalPath());
        logger.info("User current working directory [{}]", System.getProperty("user.dir"));
        logger.info("User current home directory [{}]", System.getProperty("user.home"));
        //logger.info("location of the jar file from which the current class was loaded [{}]", this.getClass().getProtectionDomain().getCodeSource().getLocation());
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.04
     *
     * @return
     * @throws IOException
     */
    private void saveAndLoad () throws IOException {
        logger.info("Temporary file  was not deleted");
    }

}
