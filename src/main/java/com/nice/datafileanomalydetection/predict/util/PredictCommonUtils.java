package com.nice.datafileanomalydetection.predict.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class PredictCommonUtils {

    // windows 환경에서 utf-8로 저장 시 BOM(Byte Order Mark)이 파일의 가장 앞부분에 추가되는 경우가 발생하는데,
    // 이를 제거하기 위해 아래의 문자를 사용하여 substring 해주면 해결됨.
    public static final String UTF8_BOM = "\uFEFF";
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    public static String getProjectPath (String projectname) {

        String rootPath = System.getProperty("user.dir");
        String projectPath = rootPath + "/model/" + projectname;
        logger.info("Anomaly Detection Model File Full Path is [{}]. ", projectPath);

        return projectPath;
    }

    // 2020-05-25 kty UTF-8 BOM 일 경우 맨 위 property 가 null 로 리턴되므로 이 값을 다시 세팅하는 함수.
    public static Properties setProperty (String inputLayoutFile) throws IOException {

        Properties properties = new Properties();
        properties.load(new FileReader(inputLayoutFile));

        if (properties.getProperty("fixedlayoutyn") == null) {
            logger.info("First Property fixedlayoutyn is null [{}]", properties.getProperty("fixedlayoutyn"));
            properties.setProperty("fixedlayoutyn", properties.getProperty(UTF8_BOM + "fixedlayoutyn"));
            properties.remove(UTF8_BOM + "fixedlayoutyn");
        }

        // 각 property 들에서 입력받은 값 사이에 공란이 있을 경우 이를 제거하여 다시 세팅.
        //List<String> arrayPropertiesList = Arrays.asList(new String[] {"header","columnlength","ratiograde","threshold","keycolumn"});
        List<String> nonArrayPropertiesList = Arrays.asList("fixedlayoutyn", "delimeter", "headertoskip", "footertoskip");
        Set<String> propertyNames = properties.stringPropertyNames();
        for (String key : propertyNames) {
            if (nonArrayPropertiesList.contains(key)) {
                properties.setProperty(key, properties.getProperty(key).trim());
            } else {
                String[] propertyValueArray = properties.getProperty(key).split(",", - 1);
                String[] newValueArray = new String[propertyValueArray.length];
                for (int i = 0; i < propertyValueArray.length; i++) {
                    newValueArray[i] = propertyValueArray[i].trim();
                }
                properties.setProperty(key, StringUtils.join(newValueArray, ','));
            }

            logger.info("Property [{}] Value [{}]", key, properties.getProperty(key));
        }

        return properties;
    }

    // 2020-05-26 kty 사용자가 데이터파일 이름만 입력하거나 레이아웃 파일 이름만 입력할 경우 각각 data/, properties/ 에서 파일을 읽어오도록 수정.
    // Model 을 상속관계로 수정 후 구현 예정.
    public static Object setDefaultDataPath (Object dataModel) {


        return dataModel;
    }

    public static long getTotalLineCount (String dataFile) throws IOException {

        long totalLineCount = 0;

        String extension = FilenameUtils.getExtension(dataFile);
        logger.info("Data File Extension is [{}]. ", extension);

        if ("gz".equals(extension)) {
            GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(new File(dataFile)));
            BufferedReader br = new BufferedReader(new InputStreamReader(gzip));
            while ((br.readLine()) != null) {
                totalLineCount++;
            }
            br.close();
        } else {
            InputStream flatFile = new FileInputStream(new File(dataFile));
            BufferedReader br = new BufferedReader(new InputStreamReader(flatFile));
            while ((br.readLine()) != null) {
                totalLineCount++;
            }
            br.close();
        }

        logger.info("Data File Line count is [{}]. ", totalLineCount);
        return totalLineCount;
    }

    public static boolean LicenseCheck () {
        return false;

    }

    public static Map<String, Object> getItemDist (String dataFile, Properties properties, final long totalLineCount, String inputFileEncoding) throws IOException {
        String line;

        int headerSkip = Integer.parseInt(properties.getProperty("headertoskip"));
        int footerSkip = Integer.parseInt(properties.getProperty("footertoskip"));
        String delimiter = properties.getProperty("delimeter");
        delimiter = "\\" + delimiter;
        String[] headerArray;
        String[] gradeItemArray;
        String[] columnLenArr;
        if ("Y".equals(properties.getProperty("fixedlayoutyn"))) {
            headerArray = properties.getProperty("header").split(",");
            gradeItemArray = properties.getProperty("ratiograde").split(",");
            columnLenArr = properties.getProperty("columnlength").split(",");
        } else {
            headerArray = properties.getProperty("header").split(",");
            if (headerArray.length <= 1) {
                headerArray = properties.getProperty("header").split(delimiter);
                if (headerArray.length <= 1) {
                    logger.info("Delimiter [{}] and Comma , does not match with data", delimiter);
                }
            }
            gradeItemArray = properties.getProperty("ratiograde").split(",");
            //2020-06-23 hjy fixedlayoutyn항목이 "N"일 경우 columnlength항목이 없을 수 있음. 따라서 초기화만 해줌.
            //columnLenArr = properties.getProperty("columnlength").split(",");
            columnLenArr = new String[0];
        }

        Map<String, Integer> gradeItemIndexMap = new HashMap<>();
        Map<String, Object> cntMapPerGrade = new HashMap<>();
        for (String item : gradeItemArray) {
            gradeItemIndexMap.put(item, ArrayUtils.indexOf(headerArray, item));
            cntMapPerGrade.put(item, new HashMap<String, Long>());
        }

        long lineCount = 0;

        String extension = FilenameUtils.getExtension(dataFile);
        logger.info("Data File Extension is [{}]. ", extension);

        if ("gz".equals(extension)) {
            GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(new File(dataFile)));
            BufferedReader br = new BufferedReader(new InputStreamReader(gzip, inputFileEncoding));
            if ("Y".equals(properties.getProperty("fixedlayoutyn"))) {
                // score 에 해당하는 문자열 자르기
                Map<String, int[]> itemBlockMap = new HashMap<>();
                for (String key : cntMapPerGrade.keySet()) {
                    int indexOfGrd = ArrayUtils.indexOf(headerArray, key);
                    int grdValLen = Integer.parseInt(columnLenArr[indexOfGrd].trim());
                    int startPoint = 0;
                    for (int i = 0; i < indexOfGrd; i++) {
                        startPoint += Integer.parseInt(columnLenArr[i].trim());
                    }
                    // 2020-04-17 kty byte값으로 직접 line substring 하도록 수정.
                    itemBlockMap.put(key, new int[]{startPoint, grdValLen});
                }

                while ((line = br.readLine()) != null) {
                    lineCount++;
                    if (lineCount > headerSkip && lineCount <= (totalLineCount - footerSkip)) {
                        for (String key : cntMapPerGrade.keySet()) {
                            Map<String, Long> itemCntMap = (Map<String, Long>) cntMapPerGrade.get(key);
                            // 2020-04-17 kty byte값으로 직접 line substring 하도록 수정.
                            String grdVal = new String(line.getBytes(inputFileEncoding), itemBlockMap.get(key)[0], itemBlockMap.get(key)[1], inputFileEncoding);
                            if (itemCntMap.containsKey(grdVal)) {
                                itemCntMap.put(grdVal, itemCntMap.get(grdVal) + 1);
                            } else {
                                itemCntMap.put(grdVal, 1L);
                            }
                        }
                    }
                }

            } else {
                // score 에 해당하는 column 번호 찾기
                //int indexOfScr = ArrayUtils.indexOf(properties.getProperty("header").split(delimiter), properties.getProperty("ratiograde"));
                while ((line = br.readLine()) != null) {
                    lineCount++;
                    if (lineCount > headerSkip && lineCount <= (totalLineCount - footerSkip)) {
                        // line 당 등급 항목들 각각 값 확인하여 count 저장
                        for (String key : cntMapPerGrade.keySet()) {
                            Map<String, Long> itemCntMap = (Map<String, Long>) cntMapPerGrade.get(key);
                            String gradeVal = line.split(delimiter, - 1)[gradeItemIndexMap.get(key)];

                            if (itemCntMap.containsKey(gradeVal)) {
                                itemCntMap.put(gradeVal, itemCntMap.get(gradeVal) + 1);
                            } else {
                                itemCntMap.put(gradeVal, 1L);
                            }
                        }

                    }

                }
            }
            br.close();

        } else {
            InputStream flatFile = new FileInputStream(new File(dataFile));
            BufferedReader br = new BufferedReader(new InputStreamReader(flatFile, inputFileEncoding));
            if ("Y".equals(properties.getProperty("fixedlayoutyn"))) {
                // score 에 해당하는 문자열 자르기
                Map<String, int[]> itemBlockMap = new HashMap<>();
                for (String key : cntMapPerGrade.keySet()) {
                    int indexOfGrd = ArrayUtils.indexOf(headerArray, key);
                    int grdValLen = Integer.parseInt(columnLenArr[indexOfGrd].trim());
                    int startPoint = 0;
                    for (int i = 0; i < indexOfGrd; i++) {
                        startPoint += Integer.parseInt(columnLenArr[i].trim());
                    }
                    // 2020-04-17 kty byte값으로 직접 line substring 하도록 수정.
                    itemBlockMap.put(key, new int[]{startPoint, grdValLen});
                }

                while ((line = br.readLine()) != null) {
                    lineCount++;
                    if (lineCount > headerSkip && lineCount <= (totalLineCount - footerSkip)) {
                        for (String key : cntMapPerGrade.keySet()) {
                            Map<String, Long> itemCntMap = (Map<String, Long>) cntMapPerGrade.get(key);

                            // 2020-04-17 kty byte값으로 직접 line substring 하도록 수정.
                            String grdVal = new String(line.getBytes(inputFileEncoding), itemBlockMap.get(key)[0], itemBlockMap.get(key)[1], inputFileEncoding);
                            if (itemCntMap.containsKey(grdVal)) {
                                itemCntMap.put(grdVal, itemCntMap.get(grdVal) + 1);
                            } else {
                                itemCntMap.put(grdVal, 1L);
                            }
                        }
                    }
                }

            } else {
                // score 에 해당하는 column 번호 찾기
                //int indexOfScr = ArrayUtils.indexOf(properties.getProperty("header").split(delimiter), properties.getProperty("ratiograde"));
                while ((line = br.readLine()) != null) {
                    lineCount++;
                    if (lineCount > headerSkip && lineCount <= (totalLineCount - footerSkip)) {
                        // line 당 등급 항목들 각각 값 확인하여 count 저장
                        for (String key : cntMapPerGrade.keySet()) {
                            Map<String, Long> itemCntMap = (Map<String, Long>) cntMapPerGrade.get(key);
                            String gradeVal = line.split(delimiter, - 1)[gradeItemIndexMap.get(key)];

                            if (itemCntMap.containsKey(gradeVal)) {
                                itemCntMap.put(gradeVal, itemCntMap.get(gradeVal) + 1);
                            } else {
                                itemCntMap.put(gradeVal, 1L);
                            }

                            //cntMapPerGrade.put(key, itemCntMap);
                        }

                    }

                }

            }
            br.close();

        }

        for (Map.Entry<String, Object> elem1 : cntMapPerGrade.entrySet()) {
            Map<String, Long> itemCntMap = (Map<String, Long>) elem1.getValue();
            for (Map.Entry<String, Long> elem2 : itemCntMap.entrySet()) {
                System.out.println(String.format("ITEM : %s, SCORE : %s, COUNT : %d", elem1.getKey(), elem2.getKey(), elem2.getValue()));
            }
        }
        return cntMapPerGrade;
    }

    public static Map<String, Object> getItemDistProb (Map<String, Object> itemValueCntMap, long totalDataCnt) {

        Map<String, Object> itemValueDistMap = new HashMap<>();

        for (Map.Entry<String, Object> elem1 : itemValueCntMap.entrySet()) {
            Map<String, Long> valueCntMap = (Map<String, Long>) elem1.getValue();

            Map<String, Double> valueRatioMap = new HashMap<>();
            for (Map.Entry<String, Long> elem2 : valueCntMap.entrySet()) {
                valueRatioMap.put(elem2.getKey(), (double) elem2.getValue() / (double) totalDataCnt);
            }

            Map<String, Object> valueDistMap = new HashMap<>();
            valueDistMap.put("count", valueCntMap);
            valueDistMap.put("prob", valueRatioMap);

            itemValueDistMap.put(elem1.getKey(), valueDistMap);

        }

        for (Map.Entry<String, Object> elem : itemValueDistMap.entrySet()) {
            Map<String, Object> tempValueDistMap = (Map<String, Object>) elem.getValue();

            Map<String, Long> tempCntMap = (Map) tempValueDistMap.get("count");
            Map<String, Double> tempProbMap = (Map) tempValueDistMap.get("prob");

            for (String key : tempCntMap.keySet()) {
                logger.info(String.format("SCORE ITEM : %s, VALUE : %s, COUNT : %d, PROB : %f", elem.getKey(), key, tempCntMap.get(key), tempProbMap.get(key)));
            }
        }

        return itemValueDistMap;
    }

    public static String findFileEncoding (String dataFile) throws IOException {
        // 시스템 인코딩 체크
        logger.info("System.getProperty('sun.jnu.encoding'): [{}] System.getProperty('file.encoding'): [{}] Charset.defaultCharset(): [{}] Locale: [{}]", System.getProperty("sun.jnu.encoding"), System.getProperty("file.encoding"), Charset.defaultCharset().name(), Locale.getDefault());
        String encoding = "";

        String extension = FilenameUtils.getExtension(dataFile);

        byte[] buf = new byte[4096];

        UniversalDetector detector = new UniversalDetector(null);
        if ("gz".equals(extension)) {
            InputStream gzip = new GZIPInputStream(new FileInputStream(new File(dataFile)));

            int nread;
            while ((nread = gzip.read(buf)) > 0 && ! detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }

            detector.dataEnd();

            gzip.close();

            encoding = detector.getDetectedCharset();
        } else {
            FileInputStream fis = new FileInputStream(new File(dataFile));

            int nread;
            while ((nread = fis.read(buf)) > 0 && ! detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }

            detector.dataEnd();

            fis.close();

            encoding = detector.getDetectedCharset();
        }

        if (encoding != null) {
            logger.info("INPUT FILE: {} ENCODING DETECTED: {}", dataFile, encoding);
        } else {
            logger.info("INPUT FILE: {} NO ENCODING DETECTED: {}", dataFile, encoding);
            encoding = Charset.defaultCharset().name();
            logger.info("INPUT FILE: {} ENCODING REPLACED: {}", dataFile, encoding);

        }

        detector.reset();

        return encoding;
    }

    public static double pnormRaw2 (double x) {
        final double Pi = 3.141592653589793238462643;
        final double a1 = 0.31938153;
        final double a2 = - 0.356563782;
        final double a3 = 1.781477937;
        final double a4 = - 1.821255978;
        final double a5 = 1.330274429;
        double L = Math.abs(x);
        double K = 1.0 / (1.0 + 0.2316419 * L);
        double w = 1.0 - 1.0 / Math.sqrt(2 * Pi) / Math.exp(L * L / 2) * (a1 * K + a2 * Math.pow(K, 2) + a3 * Math.pow(K, 3) + a4 * Math.pow(K, 4) + a5 * Math.pow(K, 5));

        return w;
    }

    public static void cleanTempDirectory () {
        String tempDirectory = System.getProperty("user.dir").replace("\\", "/") + "/tmp";
        try {
            FileUtils.cleanDirectory(new File(tempDirectory));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
