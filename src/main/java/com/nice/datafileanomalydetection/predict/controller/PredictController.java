package com.nice.datafileanomalydetection.predict.controller;

import com.nice.datafileanomalydetection.online.service.OnlineService;
import com.nice.datafileanomalydetection.predict.model.*;
import com.nice.datafileanomalydetection.predict.service.PredictService;
import com.nice.datafileanomalydetection.predict.util.PredictCommonUtils;
import com.nice.datafileanomalydetection.predict.util.TrainCommonUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.*;

@EnableBatchProcessing
@RestController
public class PredictController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job predictJob;

    @Autowired
    Job predictJobFixed;

    @Autowired
    Job makeFixedToCSVFile;

    @Autowired
    Job convertCSVtoTrainCSVJob;

    @Autowired
    Job convertFixedToTrainCSVJob;

    @Autowired
    Job convertJSONToCSVJob;

    @Autowired
    Job predictJobJson;

    @Autowired
    Job predictJobMultiJson;

    @Autowired
    Job predictTableTestJob;

    @Autowired
    PredictService predictService;

    @Autowired
    OnlineService onlineService;

    @Autowired
    PredictBatchInfo predictBatchInfoTemp;

    @Value("${result.write.enabled:false}")
    boolean resultWriteEnabled;

    @Value("${result.write.path}")
    private String resultFilePath;

    //refs #1364 JSon File의 레이아웃이 변하는 것을 대비하여 Parsing 관련 인자 프로퍼티처리, kjh, 2020-11-19
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

    @GetMapping(value = "/makecsv")
    public ModelAndView makecsvView (ModelAndView mv) {
        mv.setViewName("/watchDogMakeCsv");
        return mv;
    }

    @GetMapping(value = "/makemeta")
    public ModelAndView makemetaView (ModelAndView mv) {
        mv.setViewName("/watchDogMakeMeta");
        return mv;
    }

    @GetMapping(value = "/predict")
    public ModelAndView predictView (ModelAndView mv) {
        mv.setViewName("/watchDogPredict");
        return mv;
    }

    @GetMapping(value = "/predictmulti")
    public ModelAndView predictmultiView (ModelAndView mv) {
        mv.setViewName("/watchDogPredictMulti");
        return mv;
    }

    @GetMapping(value = "/makemodel")
    public ModelAndView makeModelView (ModelAndView mv) {
        mv.setViewName("/watchDogMakeModel");
        return mv;
    }

    @GetMapping(value = "/makeproperty")
    public ModelAndView makePropertyView (ModelAndView mv) {
        mv.setViewName("/watchDogMakeProperty");
        return mv;
    }

    @GetMapping(value = "/makelogmodel")
    public ModelAndView makeLogModelView (ModelAndView mv) {
        mv.setViewName("/watchDogMakeLogModel");
        return mv;
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.06
     *
     * @param predictInputInfo
     * @return
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobParametersInvalidException
     * @throws IOException
     */
    @ApiOperation(value = "데이터 이상감지")
    @PutMapping(value = "/anomaly/predict")
    public String anomalyPredict (@RequestBody PredictInputInfo predictInputInfo) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
        String response = "";

        // 샘플 응답Sting 생성
        response = "{";
        response += "\"projectName\":" + "\" project \",";
        response += "\"fileName\":" + "\" fileName \",";
        response += "\"fileCount\":" + "\" 5 \",";
        response += "\"psi\":" + "\" 10 \",";
        response += "\"car\":" + "\" 15 \",";
        response += "\"chiSqCnt\":" + "\"" + "" + "\"";
        response += "}";

        return response;
    }

    @ApiOperation(value = "데이터 이상감지(복수)")
    @PutMapping(value = "/anomaly/predictmulti")
    public String anomalyPredictMulti (@RequestBody List<PredictInputInfo> predictInputInfos) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
        String response = "";
        double startTime = System.currentTimeMillis();
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

        logger.info("[다중 이상감지 작업 START JOB] {}", fdf.format(new Date()));
        response = "[";
        int rowNum = 1;
        for (PredictInputInfo predictInputInfo : predictInputInfos) {
            response = response + anomalyPredict(predictInputInfo);
            response = response + ",";
        }
        response = response.substring(0, response.lastIndexOf(",")) + "]";

        double endTime = System.currentTimeMillis();
        double elapseTime = (endTime - startTime) / (double) 1000;

        logger.info("[다중 이상감지 작업 END JOB] " + fdf.format(new Date()));
        logger.info("[다중 이상감지 작업 Elapse Time] " + elapseTime / 60);

        return response;
    }

    @ApiOperation(value = "기준정보 생성")
    @PutMapping(value = "/anomaly/makemeta")
    public String anomalyMakeMeta (@RequestBody PredictInputInfo predictInputInfo) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {

        // 데이터파일과 레이아웃 파일이 파일명만 입력될 경우 data/ 와 properties/ 에서 찾도록 설정.
        if (predictInputInfo.getInputDataFile().equals(FilenameUtils.getName(predictInputInfo.getInputDataFile()))) {
            predictInputInfo.setInputDataFile((System.getProperty("user.dir") + "/data/" + predictInputInfo.getInputDataFile()).replace("\\", "/"));
            logger.info("Changed InputDataFile Path [{}]", predictInputInfo.getInputDataFile());
        }
        if (predictInputInfo.getInputLayoutFile().equals(FilenameUtils.getName(predictInputInfo.getInputLayoutFile()))) {
            predictInputInfo.setInputLayoutFile((System.getProperty("user.dir") + "/properties/" + predictInputInfo.getInputLayoutFile()).replace("\\", "/"));
            logger.info("Changed InputLayoutFile Path [{}]", predictInputInfo.getInputLayoutFile());
        }

        // file encoding 체크
        predictInputInfo.setInputFileEncoding(PredictCommonUtils.findFileEncoding(predictInputInfo.getInputDataFile()));

        return predictService.makeAnomalyMetaData(predictInputInfo);
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.06
     *
     * @param makeCSVInputInfo
     * @return
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobParametersInvalidException
     * @throws IOException
     */
    @ApiOperation(value = "CSV파일 만들기")
    @PutMapping(value = "/anomaly/makecsvfile")
    public String anomalyMakeCSVFile (@RequestBody MakeCSVInputInfo makeCSVInputInfo) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {

        // TODO 응답 String 샘플데이터로 셋팅하기! - kjh, 2021.05.06
        return "COMPLETED";
    }

    @GetMapping(value = "/anomaly/getfiles")
    public List<String> anomalyGetFiles (@RequestParam Map<String, String> paramMap) {

        final String dirName = paramMap.get("dirname");
        final String pattern = paramMap.get("pattern");
        //final String pattern = ".*\\.properties";

        final File folder = new File(dirName);

        List<String> result = new ArrayList<>();

        // search
        for (final File f : folder.listFiles()) {
            if (f.isFile()) {
                if (f.getName().matches(pattern)) {
                    result.add(f.getAbsolutePath());
                }
            }
        }
        return result;
    }

    @GetMapping(value = "/anomaly/checkMeta")
    public boolean anomalyCheckMeta (@RequestParam Map<String, String> paramMap) {

        final String projectName = paramMap.get("projectName");
        List<String> projectNameList = predictService.checkMeta(projectName);

        boolean isExistedMeta;

        // 리스트가 비어있으면 projectName에 해당하는 기준정보가 없는 것으로 판단
        isExistedMeta = projectNameList.size() != 0;

        return isExistedMeta;
    }

    /**
     * csv 를 padding이 제거된 csv 로 변환하는 메소드
     * 외부제공용 메소드 수정, kjh, 2020.05.06
     *
     * @param trainInputInfo
     * @return
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobParametersInvalidException
     * @throws IOException
     */
    @PutMapping(value = "/anomaly/removecsvpad")
    public String removeCSVNonPad (@RequestBody TrainInputInfo trainInputInfo) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {

        // TODO 응답 String 샘플데이터로 셋팅하기! - kjh, 2021.05.06
        return "COMPLETED";
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.04
     *
     * @param trainFromLogInfo
     * @return
     * @throws IOException
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobParametersInvalidException
     */
    @ApiOperation(value = "json 을 csv 로 변환")
    @PutMapping(value = "/anomaly/convertjsontocsv")
    public Map<String, List<String>> convertJSONToCSV (@RequestBody TrainFromLogInfo trainFromLogInfo) throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

        // file encoding 체크
        String inputFileEncoding = PredictCommonUtils.findFileEncoding(trainFromLogInfo.getLogFile());

        // file line 체크
        long totalLineCount = PredictCommonUtils.getTotalLineCount(trainFromLogInfo.getLogFile());

        // refs #1364 JSon File의 레이아웃이 변하는 것을 대비하여 Parsing 관련 인자 변수처리, kjh, 2020-11-19
        Map<String, List<String>> headerAndTypeMap = TrainCommonUtils.getHeaderAndTypeFromJson(trainFromLogInfo, inputFileEncoding, jsonItemList, jsonItemCode, jsonItemType);

        return headerAndTypeMap;
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.06
     *
     * @param trainFromLogInfo(LogFileList,ProjectName,TargetKeys,KeyItems)
     * @return
     */
    @PutMapping(value = "/anomaly/trainlogmodel")
    public String trainModelFromLogFiles (@RequestBody TrainFromLogInfo trainFromLogInfo) {

        return predictService.trainJsonOnlineLogFile(trainFromLogInfo);
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.06
     * @param trainFromLogInfo
     * @return 이상감지 수행 결과, 0: 정상, 1: 이상
     */
    @PutMapping(value = "/anomaly/predictjson")
    public int predictFromOnlineLogFile (@RequestBody TrainFromLogInfo trainFromLogInfo) {
        int result = 0;
        // TODO 응답 샘플데이터로 셋팅하기! - kjh, 2021.05.06
        return result;
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.04
     *
     * @param trainFromLogInfos
     * @return
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobParametersInvalidException
     * @throws IOException
     */
    @ApiOperation(value = "JSON Predict Multi")
    @PutMapping(value = "/anomaly/predictjsonrept")
    public int[] PredictFromMultiLogFile (@RequestBody List<TrainFromLogInfo> trainFromLogInfos) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
        int[] res = new int[trainFromLogInfos.size()];
        // TODO: res에 따라 화면에서 나오는 내용이 다름. 샘플 res 준비. kjh, 2021.05.06
        return res;
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.06
     *
     * @param trainFromLogInfo
     * @return
     * @throws IOException
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobParametersInvalidException
     */
    @ApiOperation(value = "JSON Predict")
    @PutMapping(value = "/anomaly/predictmultijson")
    public int PredictFromMultiLogFile (@RequestBody TrainFromLogInfo trainFromLogInfo) throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        double startTime = System.currentTimeMillis();

        int res = 0;
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

        double endTime = System.currentTimeMillis();
        double elapseTime = (endTime - startTime) / (double) 1000;

        logger.info("[END JOB] " + fdf.format(new Date()));
        logger.info("[Elapse Time] " + elapseTime / 60);

        // TODO: res에 따라 화면에서 나오는 내용이 다름. 샘플 res 준비. kjh, 2021.05.06
        return res;
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.06
     *
     * @param trainFromLogInfo
     * @return
     * @throws IOException
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobParametersInvalidException
     */
    @PutMapping(value = "/anomaly/predicttable")
    public int predictTable (@RequestBody TrainFromLogInfo trainFromLogInfo) throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {

        double startTime = System.currentTimeMillis();

        int res = 0;
        String comment = "";
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        double endTime = System.currentTimeMillis();
        double elapseTime = (endTime - startTime) / (double) 1000;

        logger.info("[END JOB] " + fdf.format(new Date()));
        logger.info("[Elapse Time] " + elapseTime / 60);

        // TODO: res에 따라 화면에서 나오는 내용이 다름. 샘플 res 준비. kjh, 2021.05.06
        return res;

    }

    @ApiOperation(value = "Property파일 생성")
    @PutMapping(value = "/anomaly/makeproperty")
    public String makePropertyFile (@RequestBody PropertyInfo propertyInfo) {
        return predictService.makePropertyFile(propertyInfo);
    }

    @GetMapping(value = "/anomaly/loadproperty/{propertyName}")
    public PropertyInfo loadProperty (@PathVariable String propertyName) throws IOException {
        return predictService.loadProperty(propertyName);
    }

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.04
     *
     * @param trainInputInfo
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobParametersInvalidException
     */
    @PutMapping(value = "/anomaly/makemodel")
    public String trainModel (@RequestBody TrainInputInfo trainInputInfo) throws IOException, InterruptedException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        //(RequestBody PredictInputInfo predictInputInfo)
        if ("Y".equals(trainInputInfo.getMakeModelYN())) {
            logger.info("PROJECT FILE EXTENSION [{}]", FilenameUtils.getExtension(trainInputInfo.getProjectName()));
        }
        // 데이터파일과 레이아웃 파일이 파일명만 입력될 경우 data/ 와 properties/ 에서 찾도록 설정.
        if (trainInputInfo.getInputDataFile().equals(FilenameUtils.getName(trainInputInfo.getInputDataFile()))) {
            trainInputInfo.setInputDataFile((System.getProperty("user.dir") + "/data/" + trainInputInfo.getInputDataFile()).replace("\\", "/"));
            logger.info("Changed InputDataFile Path [{}]", trainInputInfo.getInputDataFile());
        }
        if (trainInputInfo.getInputLayoutFile().equals(FilenameUtils.getName(trainInputInfo.getInputLayoutFile()))) {
            trainInputInfo.setInputLayoutFile((System.getProperty("user.dir") + "/properties/" + trainInputInfo.getInputLayoutFile()).replace("\\", "/"));
            logger.info("Changed InputLayoutFile Path [{}]", trainInputInfo.getInputLayoutFile());
        }

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("projectName", trainInputInfo.getProjectName());

        String res = "";
        // TODO 응답 String 샘플데이터로 셋팅하기! - kjh, 2021.05.03
        return res;
    }

}
