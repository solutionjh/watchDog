package com.nice.datafileanomalydetection.predict.service;

import com.nice.datafileanomalydetection.online.model.OnlineResult;
import com.nice.datafileanomalydetection.online.service.OnlineService;
import com.nice.datafileanomalydetection.predict.dao.PredictDao;
import com.nice.datafileanomalydetection.predict.model.*;
import com.nice.datafileanomalydetection.predict.util.PredictCommonUtils;
import com.nice.datafileanomalydetection.predict.util.TrainCommonUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class PredictService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    PredictDao predictDao;

    @Autowired
    OnlineService onlineService;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    PredictBatchInfo predictBatchInfoTemp;

    @Autowired
    PredictOnlineInfo predictOnlineInfo;

    @Autowired
    Job predictJob;

    @Autowired
    Job predictJobJson;

    @Autowired
    Job predictJobFixed;

    @Autowired
    Job predictTableTestJob;

    @Autowired
    Job convertJSONToCSVJob;

    @Value("${result.write.path}")
    private String resultFilePath;

    @Value("${server.address}")
    private String serverIp;

    @Value("${json.itemlist}")
    private String jsonItemList;

    @Value("${json.itemcode}")
    private String jsonItemCode;

    @Value("${json.itemtype}")
    private String jsonItemType;

    //refs #1407 사용할 프로세서 수, java path, genmodel path 등을 외부에서 입력받아 사용하도록 수정
    @Value("${predict.processors:#{T(java.lang.Math).max(T(java.lang.Runtime).getRuntime().availableProcessors()/2,2)}}")
    private int processorsToUse;

    @Value("${watchdog.java.home}")
    private String javaHome;

    @Value("${watchdog.niceml.path}")
    private String genModelPath;

    public Map<String, Object> calculateItemDistribution (PredictInputInfo predictInputInfo, Properties properties, long totalLineCount, long totalDataCount, String inputFileEncoding) throws IOException {

        logger.info("Calculate Score Distribution Start.");
        Map<String, Object> itemValueCntMap = PredictCommonUtils.getItemDist(predictInputInfo.getInputDataFile(), properties, totalLineCount, inputFileEncoding);

        logger.info("Calculate Item Probability start.");
        return PredictCommonUtils.getItemDistProb(itemValueCntMap, totalDataCount);
    }

    public ConcurrentHashMap<String, Double> getSquaredErrors (ConcurrentHashMap<String, ConcurrentHashMap<String, Double>> rowSquaredErrorInfos, List staticSquaredErrors) {

        ConcurrentHashMap<String, Double> sumSquaredErrors = rowSquaredErrorInfos.get(PredictServiceConfig.ROW_SQUARD_ERROR_LIST_MAP_KEY);

        for (Object aa : staticSquaredErrors) {
            ConcurrentHashMap<String, Double> bb = (ConcurrentHashMap<String, Double>) aa;
            for (String key : bb.keySet()) {
                double se = sumSquaredErrors.get(key);
                se = se + bb.get(key);
                sumSquaredErrors.put(key, se);
            }
        }

        return sumSquaredErrors;

    }

    public void insertJob (PredictBatchInfo predictBatchInfo) {
        predictDao.insertJob(predictBatchInfo);
    }

    public int deleteDevMeanSquardError (String projectName) {
        return predictDao.deleteDevMeanSquardError(projectName);
    }

    public int deleteDevColMeanSquardError (String projectName) {
        return predictDao.deleteDevColMeanSquardError(projectName);
    }

    public int deleteDevItemDistribution (PredictInputInfo predictInputInfo) {
        logger.info("항목 분포 기존 기준정보 삭제.");

        return predictDao.deleteDevItemDistribution(predictInputInfo);
    }

    public int insertDevMeanSquardError (String projectName, String regDtim) {
        return predictDao.insertDevMeanSquardError(projectName, regDtim);
    }

    public int insertDevColMeanSquardError (String projectName, String regDtim) {
        return predictDao.insertDevColMeanSquardError(projectName, regDtim);
    }

    public void insertDevItemDistribution (PredictInputInfo predictInputInfo, Map<String, Object> itemValueDistMap) {

        // 항목 분포 기준정보 DB 등록
        logger.info("항목 분포 기준정보 등록");

        for (Map.Entry<String, Object> elem : itemValueDistMap.entrySet()) {
            Map<String, Object> valueDistMap = (Map<String, Object>) elem.getValue();

            Map<String, Long> valueCntMap = (Map<String, Long>) valueDistMap.get("count");
            Map<String, Double> valueProbMap = (Map<String, Double>) valueDistMap.get("prob");

            for (String key : valueCntMap.keySet()) {
                ItemDistInfo itemDistInfo = new ItemDistInfo();
                itemDistInfo.setProjectName(predictInputInfo.getProjectName());
                itemDistInfo.setFieldName(elem.getKey());
                itemDistInfo.setItemValue(key);
                itemDistInfo.setItemCount(valueCntMap.get(key));
                itemDistInfo.setItemProb(valueProbMap.get(key));

                logger.info("FieldName [{}], ItemValue [{}], ItemCount [{}], ItemProb [{}]", elem.getKey(), key, valueCntMap.get(key), valueProbMap.get(key));

                predictDao.insertDevItemDistribution(itemDistInfo);
            }
        }

    }

    public void insertTotItemDistribution (String projectName, String regDtim, Map<String, Object> itemValueDistMap) {
        logger.info("항목 분포 결과정보 등록");

        for (Map.Entry<String, Object> elem : itemValueDistMap.entrySet()) {
            Map<String, Object> valueDistMap = (Map<String, Object>) elem.getValue();

            Map<String, Long> valueCntMap = (Map<String, Long>) valueDistMap.get("count");
            Map<String, Double> valueProbMap = (Map<String, Double>) valueDistMap.get("prob");

            ItemDistInfo itemDistInfo = new ItemDistInfo();
            for (String key : valueCntMap.keySet()) {
                itemDistInfo.setProjectName(projectName);
                itemDistInfo.setRegDtim(regDtim);
                itemDistInfo.setFieldName(elem.getKey());
                itemDistInfo.setItemValue(key);
                itemDistInfo.setItemCount(valueCntMap.get(key));
                itemDistInfo.setItemProb(valueProbMap.get(key));

                logger.info("FieldName [{}], ItemValue [{}], ItemCount [{}], ItemProb [{}]", elem.getKey(), key, valueCntMap.get(key), valueProbMap.get(key));

                predictDao.insertTotItemDistribution(itemDistInfo);
            }

        }

    }

    public int doChiSqTest (String projectName, String regDtim, Map<String, Object> itemValueDistMap, long totalDataCount, ThresholdInfo thresholdInfo) {

        int chiSqCnt = 0;
        for (Map.Entry<String, Object> elem : itemValueDistMap.entrySet()) {
            Map<String, Object> valueDistMap = (Map<String, Object>) elem.getValue();

            Map<String, Long> valueCntMap = (Map<String, Long>) valueDistMap.get("count");
            Map<String, Double> valueProbMap = (Map<String, Double>) valueDistMap.get("prob");

            ItemDistInfo itemDistInfo = new ItemDistInfo();
            for (String key : valueCntMap.keySet()) {
                itemDistInfo.setProjectName(projectName);
                itemDistInfo.setRegDtim(regDtim);
                itemDistInfo.setFieldName(elem.getKey());
                itemDistInfo.setItemValue(key);
                itemDistInfo.setItemCount(valueCntMap.get(key));
                itemDistInfo.setItemProb(valueProbMap.get(key));
                itemDistInfo.setTotalDataCnt(totalDataCount);

            }

            logger.info("카이제곱 검정");
            Map<String, Object> chisqRsltMap = predictDao.calculateChiSqStatistic(itemDistInfo);
            double chiSqStatistic = (double) chisqRsltMap.get("CHISQSUM");
            long newValueCnt = (long) chisqRsltMap.get("NEWVALCNT");

            logger.info("스코어 항목: [{}], 카이제곱 검정통계량: [{}], 기준정보 미존재 스코어 값 종류: [{}]", elem.getKey(), chiSqStatistic, newValueCnt);
            // p-value 계산
            int degreeOfFreedom = valueCntMap.size() - 1;
            ChiSquaredDistribution chiSqDistribution;
            double chiSqPValue = 1.0;
            if (degreeOfFreedom > 0) {
                chiSqDistribution = new ChiSquaredDistribution(degreeOfFreedom);
                chiSqPValue = 1 - chiSqDistribution.cumulativeProbability(chiSqStatistic);
                logger.info("카이제곱 검정 P-Value [{}] 분포 이상 여부 [{}]", chiSqPValue, chiSqPValue < 0.05);
            } else {
                logger.info("항목 [{}] 는 자유도가 [{}] 이기 때문에 카이제곱 검정 불가. 기준정보에 없는 새로운 값은 [{}] 개", elem.getKey(), degreeOfFreedom, newValueCnt);
            }

            // 조회를 위한 DB에 기록
            ChisqInfo chisqInfo = new ChisqInfo();

            // p-value 나 자유도 사용하는 것으로 바꾸어야 함.
            if (chiSqStatistic > thresholdInfo.getChiSqThreshold() || newValueCnt > 0.0) {
                chiSqCnt++;
                chisqInfo.setAnomalyRslt(1);
            } else {
                chisqInfo.setAnomalyRslt(0);
            }

            chisqInfo.setProjectName(projectName);
            chisqInfo.setRegDtim(regDtim);
            chisqInfo.setFieldName(elem.getKey());
            chisqInfo.setDof(degreeOfFreedom);
            chisqInfo.setChisqStat(chiSqStatistic);
            chisqInfo.setpValue(chiSqPValue);
            chisqInfo.setNewValueCnt(newValueCnt);

            predictDao.insertChisqResult(chisqInfo);

        }

        return chiSqCnt;
    }

    public int truncateRowMeanSquardError () {
        return predictDao.truncateRowMeanSquardError();
    }

    public void insertRowSquardError (String projectName, String regDtim, ConcurrentHashMap<String, Double> squaredErrors, long totalDataCount) {
        logger.info("squaredErrors size : [{}]", squaredErrors.size());
        for (String keyset : squaredErrors.keySet()) {
            RowSquaredErrorInfo rowSquaredErrorInfo = new RowSquaredErrorInfo();
            rowSquaredErrorInfo.setProjectName(projectName);
            rowSquaredErrorInfo.setRegDtim(regDtim);
            rowSquaredErrorInfo.setFieldName(keyset);
            rowSquaredErrorInfo.setSquaredError(squaredErrors.get(keyset));
            rowSquaredErrorInfo.setCount(totalDataCount);

            logger.debug("FieldName [{}], SquaredError [{}]", keyset, squaredErrors.get(keyset));

            predictDao.insertRowSquardError(rowSquaredErrorInfo);

        }

    }

    public int insertInitRowSquardError (RowSquaredErrorInfo rowSquaredErrorInfo) {
        return predictDao.insertRowSquardError(rowSquaredErrorInfo);
    }

    public int updateRowSquardError (RowSquaredErrorInfo rowSquaredErrorInfo) {
        return predictDao.updateRowSquardError(rowSquaredErrorInfo);
    }

    public int truncateRowSquardError () {
        return predictDao.truncateRowSquardError();
    }

    public int deleteTotMeanSquardError (String projectName, String regDtim) {
        return predictDao.deleteTotMeanSquardError(projectName, regDtim);
    }

    public int deleteTotItemDistribution (String projectName, String regDtim) {
        return predictDao.deleteTotItemDistribution(projectName, regDtim);
    }

    public int deleteTotColMeanSquardError (String projectName, String regDtim) {
        return predictDao.deleteTotColMeanSquardError(projectName, regDtim);
    }

    public int insertRowMeanSquardError (RowMeanSquaredErrorInfo rowMeanSquardError) {
        return predictDao.insertRowMeanSquardError(rowMeanSquardError);
    }

    public int insertTotMeanSquardError (String projectName, String regDtim) {
        return predictDao.insertTotMeanSquardError(projectName, regDtim);
    }

    public int insertTotColMeanSquardError (String projectName, String regDtim) {
        return predictDao.insertTotColMeanSquardError(projectName, regDtim);
    }

    public void updateJobStatus (PredictBatchInfo predictBatchInfo) {
        predictDao.updateJobStatus(predictBatchInfo);
    }

    public void updateJobComment (PredictBatchInfo predictBatchInfo) {
        predictDao.updateJobComment(predictBatchInfo);
    }

    public double calculatePSI (String projectName, String regDtim) {
        return predictDao.calculatePSI(projectName, regDtim);
    }

    public int calculateCAR (String projectName, String regDtim, String carThreshold) {
        return predictDao.calculateCAR(projectName, regDtim, carThreshold);
    }

    public Map<String, Object> calculateChiSqStatistic (ItemDistInfo itemDistInfo) {
        return predictDao.calculateChiSqStatistic(itemDistInfo);
    }

    public List<String> checkMeta (String projectName) {
        return predictDao.checkMeta(projectName);
    }

    public int insertChisqResult (ChisqInfo chisqInfo) {
        return predictDao.insertChisqResult(chisqInfo);
    }

    public int insertTrainDataSummary (ModelDataInfo modelDataInfo) {
        return predictDao.insertTrainDataSummary(modelDataInfo);
    }

    public int deleteTrainDataSummary (ModelDataInfo modelDataInfo) {
        String projectName = modelDataInfo.getProjectName();
        return predictDao.deleteTrainDataSummary(projectName);
    }

    public int updateTrainDataSummary (ModelDataInfo modelDataInfo) {
        return predictDao.updateTrainDataSummary(modelDataInfo);
    }

    public List<String> selectModelFields (String projectName) {
        return predictDao.selectModelFields(projectName);
    }

    public String makePropertyFile (PropertyInfo propertyInfo) {

        String projectName = propertyInfo.getFileName();

        Properties properties = new Properties();
        String prop = propertyInfo.getFixedLayoutYN();
        if (prop != null && ! prop.isEmpty()) {
            properties.setProperty("fixedlayoutyn", prop);
        }

        prop = propertyInfo.getHeader();
        if (prop != null && ! prop.isEmpty()) {
            properties.setProperty("header", prop);
        }

        prop = propertyInfo.getColumnLength();
        if (prop != null && ! prop.isEmpty()) {
            properties.setProperty("columnlength", prop);
        }

        prop = propertyInfo.getDelimiter();
        if (prop != null && ! prop.isEmpty()) {
            properties.setProperty("delimeter", prop);
        }

        prop = propertyInfo.getHeaderToSkip();
        if (prop != null && ! prop.isEmpty()) {
            properties.setProperty("headertoskip", prop);
        }

        prop = propertyInfo.getFooterToSkip();
        if (prop != null && ! prop.isEmpty()) {
            properties.setProperty("footertoskip", prop);
        }

        prop = propertyInfo.getRatioGrade();
        if (prop != null && ! prop.isEmpty()) {
            properties.setProperty("ratiograde", prop);
        }

        prop = propertyInfo.getThreshold();
        if (prop != null && ! prop.isEmpty()) {
            properties.setProperty("threshold", prop);
        }

        prop = propertyInfo.getKeyColumn();
        if (prop != null && ! prop.isEmpty()) {
            properties.setProperty("keycolumn", prop);
        }

        prop = propertyInfo.getRequiredColumn();
        if (prop != null && ! prop.isEmpty()) {
            properties.setProperty("requiredcolumn", prop);
        }

        // 파일 저장
        String propertyFileStr = System.getProperty("user.dir").replace("\\", "/") + "/properties/" + projectName + ".properties";
        try (OutputStream outputStream = new FileOutputStream(propertyFileStr)) {
            properties.store(outputStream, "This is properties file for project " + projectName);
            logger.info("Property file saved [{}]", propertyFileStr);
        } catch (IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }

        return propertyFileStr;
    }

    public PropertyInfo loadProperty (String propertyName) throws IOException {
        String inputLayoutFile = System.getProperty("user.dir").replace("\\", "/") + "/properties/" + propertyName;

        Properties properties = PredictCommonUtils.setProperty(inputLayoutFile);

        return new PropertyInfo(properties);
    }

    public int insertRowMeanProb (RowMeanProbInfo rowMeanProbability) {
        return predictDao.insertRowMeanProb(rowMeanProbability);
    }

    public int truncateRowMeanProb () {
        return predictDao.truncateRowMeanProb();
    }

    public Map<String, Object> checkAnomaly (String projectName, String regDtim, double probThreshold) {
        Map<String, Object> anomalyInfo = predictDao.calculateAnomalyRate(projectName, regDtim, probThreshold);
        double anomalyRate = (double) anomalyInfo.get("ANOMALY_RATIO");

        logger.info("Project [{}] RegDtim [{}] Anomaly Rate [{}]", projectName, regDtim, anomalyRate);
        return anomalyInfo;
    }

    public int insertRowProbStat (KeyInfo keyInfo) {
        return predictDao.insertRowProbStat(keyInfo);
    }

    public ConcurrentHashMap<String, Double> getItemSEProbSums (List staticItemSEProbs) {

        ConcurrentHashMap<String, Double> itemSEProbSums = new ConcurrentHashMap<String, Double>();

        for (Object aa : staticItemSEProbs) {
            ConcurrentHashMap<String, Double> bb = (ConcurrentHashMap<String, Double>) aa;
            for (String key : bb.keySet()) {
                double seProb;
                try {
                    seProb = itemSEProbSums.get(key);
                } catch (NullPointerException ne) {
                    seProb = 0.0;
                }
                seProb += bb.get(key);
                itemSEProbSums.put(key, seProb);
            }
        }

        return itemSEProbSums;
    }

    public void insertItemSEMeanProb (String projectName, String pkgId, String inputDate, String regDtim, ConcurrentHashMap<String, Double> itemSEProbSums, long totalDataCount) {

        for (Map.Entry<String, Double> elem : itemSEProbSums.entrySet()) {
            String fieldName = elem.getKey();
            double meanSEProb = elem.getValue() / totalDataCount;

            predictDao.insertItemSEMeanProb(projectName, pkgId, inputDate, regDtim, fieldName, meanSEProb);
        }

    }

    public List<String> checkItemAnomaly (String projectName, String regDtim, double probThreshold) {
        return predictDao.checkItemAnomaly(projectName, regDtim, probThreshold);
    }

    public int insertOnlineResult (OnlineResult onlineResult) {
        return predictDao.insertOnlineResult(onlineResult);
    }

    public int insertRowProbDist (KeyInfo keyInfo) {
        return predictDao.insertRowProbDist(keyInfo);
    }

    public int insertDevRowProbDist (KeyInfo keyInfo) {
        return predictDao.insertDevRowProbDist(keyInfo);
    }

    public int deleteDevRowProbDist (KeyInfo keyInfo) {
        return predictDao.deleteDevRowProbDist(keyInfo);
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.06
     * @param trainFromLogInfo   projectName, logFile, pkgId, InputDate, keyItems, targetKeys, anomalyCarCnt, columnCalculateYN
     * @param resultWriteEnabled
     * @return
     * @throws IOException
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobParametersInvalidException
     */
    public int predictJsonOnlineLogFile (TrainFromLogInfo trainFromLogInfo,
                                         boolean resultWriteEnabled) throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        int result = 0;
        // TODO 응답 String 샘플데이터로 셋팅하기! - kjh, 2021.05.06
        return result;
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.06
     * @param trainFromLogInfo
     * @return
     * @author kjh
     * @since 2020.11.02
     */
    public String trainJsonOnlineLogFile (TrainFromLogInfo trainFromLogInfo) {
        String result = "";
        // TODO 응답 String 샘플데이터로 셋팅하기! - kjh, 2021.05.06
        return result;
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.06
     * @param trainFromLogInfo
     * @return
     * @throws IOException
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobParametersInvalidException
     */
    public Map<String, List<String>> convertJsonToCSV (@RequestBody TrainFromLogInfo trainFromLogInfo) throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

        // file encoding 체크
        String inputFileEncoding = PredictCommonUtils.findFileEncoding(trainFromLogInfo.getLogFile());

        // file line 체크
        long totalLineCount = PredictCommonUtils.getTotalLineCount(trainFromLogInfo.getLogFile());

        // refs #1364 JSon File의 레이아웃이 변하는 것을 대비하여 Parsing 관련 인자 변수처리, kjh, 2020-11-19
        Map<String, List<String>> headerAndTypeMap = TrainCommonUtils.getHeaderAndTypeFromJson(trainFromLogInfo, inputFileEncoding, jsonItemList, jsonItemCode, jsonItemType);
        logger.info("FROM JSON ,HEADER IS [{}]", StringUtils.join(headerAndTypeMap.get("headerList"), ","));

        // TODO 응답 Map 샘플데이터로 셋팅하기! - kjh, 2021.05.06
        return headerAndTypeMap;
    }

    /**
     * JSon에서 CSV로 변경한 파일을 바탕으로 기초 통계량 저장 및 이상감지 모형 학습
     * 외부제공용 메소드 수정, kjh, 2020.05.04
     *
     * @param trainModelInfo
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @author kjh
     * @since 2020.11.02
     */

    public int trainFromLogCSV (TrainModelInfo trainModelInfo) throws IOException, InterruptedException {

        int trainResult = 0;

        // TODO 응답 String 샘플데이터로 셋팅하기! - kjh, 2021.05.04
        return trainResult;
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.04
     *
     * @param predictInputInfo
     * @return
     * @throws IOException
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobParametersInvalidException
     */
    public String makeAnomalyMetaData (PredictInputInfo predictInputInfo) throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        // TODO 응답 String 샘플데이터로 셋팅하기! - kjh, 2021.05.03
        return "Result";
    }

    /**
     * 모형에 사용할 항목을 선별하여 코드로 응답
     * 외부제공용 메소드 수정, kjh, 2020.05.04
     *
     * @return M: 의미있는 항목, U: 사용자 선택항목, N: 미사용 항목
     * @author kjh
     * @since 2020.11.02
     */
    private String checkUseColumn () {
        String result = "M";

        return result;
    }
}
