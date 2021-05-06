package com.nice.datafileanomalydetection.predict.service.listener;

import com.nice.datafileanomalydetection.predict.model.PredictBatchInfo;
import com.nice.datafileanomalydetection.predict.model.RowSquaredErrorInfo;
import com.nice.datafileanomalydetection.predict.service.PredictService;
import com.nice.datafileanomalydetection.predict.service.PredictServiceConfig;
import com.nice.datafileanomalydetection.predict.service.modelloader.AnomalyDetectionModelLoader;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


@Component("ruleStepExecutionListener")
@StepScope
public class PredictStepExecutionListener extends StepExecutionListenerSupport {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    PredictService predictService;

    StepExecution stepExecution;
    @Autowired
    PredictBatchInfo predictBatchInfoTemp;
    @Value("#{jobParameters['projectName']}")
    private String projectName;

    @Override
    public ExitStatus afterStep (StepExecution stepExecution) {
        NumberFormat nf = NumberFormat.getNumberInstance();

        logger.info("{} count read", nf.format(stepExecution.getReadCount()));
        logger.info("{} count write", nf.format(stepExecution.getWriteCount()));

        // TODO PREDICT_ROW_SQUARDERROR 등 임시테이블 삭제

        return stepExecution.getExitStatus();
    }

    @Override
    public void beforeStep (StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        logger.info(stepExecution.getSummary());
        logger.info("availableProcessors: {}", Runtime.getRuntime().availableProcessors());

        try {


            String projectPath = PredictServiceConfig.getModelFileFullPath(projectName);
            logger.info("Anomaly Detection Model File Full Path is [{}]. ", projectPath);

            // 배치가 수행되면 병렬로 처리가 되므로 여기서, 사전에 모델을 로딩한다.
            AnomalyDetectionModelLoader loader = new AnomalyDetectionModelLoader();
            loader.anomalyDetectionModelLoad(projectPath);

            // predictBatchInfo 를 대체하도록 executionContext 에 변수 세팅.
            predictBatchInfoTemp.staticSquaredErrors = new ArrayList();

            String modelClassName = loader.getAnomalyDetectionModelClassName(projectPath);
            // model class name
            this.stepExecution.getExecutionContext().put("modelClassName", modelClassName);

            rowSquardErrorHashMapInit();

        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }

    }

    private void rowSquardErrorHashMapInit () throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        String modelClassName = this.stepExecution.getExecutionContext().getString("modelClassName");

        Class klass = Thread.currentThread().getContextClassLoader().loadClass(modelClassName);
        Object obj = klass.newInstance();

        Method mParameters = klass.getMethod("getNames");
        Object parametersObj = mParameters.invoke(obj, null);
        final String[] parameters = (String[]) parametersObj;

        logger.info("Columns used in model [{}] [{}]", parameters.length, String.join(",", parameters));

        ConcurrentHashMap<String, ConcurrentHashMap<String, Double>> rowSquaredErrorInfos = new ConcurrentHashMap<String, ConcurrentHashMap<String, Double>>();
        ConcurrentHashMap<String, Double> squaredError = new ConcurrentHashMap<String, Double>();
        int i = 0;
        for (final String parameter : parameters) {
            squaredError.put(parameter, 0.0);
        }
        rowSquaredErrorInfos.put(PredictServiceConfig.ROW_SQUARD_ERROR_LIST_MAP_KEY, squaredError);

        this.stepExecution.getExecutionContext().put("rowSquaredErrorInfos", rowSquaredErrorInfos);

    }

    private void rowSquardErrorTableInit (String projectName, String regDtim, String modelClassName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

        predictService.truncateRowSquardError();

        Class klass = Thread.currentThread().getContextClassLoader().loadClass(modelClassName);
        Object obj = klass.newInstance();

        Method mParameters = klass.getMethod("getNames");
        Object parametersObj = mParameters.invoke(obj, null);
        final String[] parameters = (String[]) parametersObj;

        int i = 0;
        for (final String parameter : parameters) {

            RowSquaredErrorInfo rowSquaredError = new RowSquaredErrorInfo();

            rowSquaredError.setProjectName(projectName);
            rowSquaredError.setRegDtim(regDtim);
            rowSquaredError.setFieldName(parameter);
            rowSquaredError.setSquaredError(0);

            predictService.insertInitRowSquardError(rowSquaredError);
        }

    }

}
