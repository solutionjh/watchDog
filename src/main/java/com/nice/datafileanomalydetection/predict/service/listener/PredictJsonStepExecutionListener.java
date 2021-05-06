package com.nice.datafileanomalydetection.predict.service.listener;

import com.nice.datafileanomalydetection.predict.model.PredictOnlineInfo;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component("predictJsonStepExecutionListener")
@StepScope
public class PredictJsonStepExecutionListener extends StepExecutionListenerSupport {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    StepExecution stepExecution;
    @Autowired
    PredictOnlineInfo predictOnlineInfo;
    @Value("#{jobParameters['projectName']}")
    private String projectName;

    @Override
    public ExitStatus afterStep (StepExecution stepExecution) {
        NumberFormat nf = NumberFormat.getNumberInstance();

        logger.info("{} count read", nf.format(stepExecution.getReadCount()));
        logger.info("{} count write", nf.format(stepExecution.getWriteCount()));

        // return null;
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

            AnomalyDetectionModelLoader loader = new AnomalyDetectionModelLoader();
            loader.anomalyDetectionModelLoad(projectPath);

            predictOnlineInfo.setStaticSquaredErrors(projectName, new CopyOnWriteArrayList<ConcurrentHashMap<String, Double>>());
            // // refs #1283 online 이상탐지를 위하여 표준화하여 항목별 확률값 계산 (2020-06-26, kty)
            predictOnlineInfo.setStaticItemSEProbs(projectName, new CopyOnWriteArrayList<ConcurrentHashMap<String, Double>>());

            String modelClassName = loader.getAnomalyDetectionModelClassName(projectPath);
            rowSquardErrorHashMapInit(modelClassName);

            predictOnlineInfo.setModelClassName(projectName, modelClassName);
            predictOnlineInfo.setRowContainNullCnt(projectName, new Long(0));
            predictOnlineInfo.setTargetDataCnt(projectName, new Long(0));
            predictOnlineInfo.setNullItemCnt(projectName, new Integer(0));

        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    private void rowSquardErrorHashMapInit (String modelClassName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class klass = Thread.currentThread().getContextClassLoader().loadClass(modelClassName);
        Object obj = klass.newInstance();

        Method mParameters = klass.getMethod("getNames");
        Object parametersObj = mParameters.invoke(obj, null);
        final String[] parameters = (String[]) parametersObj;

        logger.info("Columns used in model [{}] [{}]", parameters.length, String.join(",", parameters));
        predictOnlineInfo.setModelItemCnt(projectName, parameters.length);

        ConcurrentHashMap<String, ConcurrentHashMap<String, Double>> rowSquaredErrorInfos = new ConcurrentHashMap<String, ConcurrentHashMap<String, Double>>();
        ConcurrentHashMap<String, Double> squaredError = new ConcurrentHashMap<String, Double>();
        int i = 0;
        for (final String parameter : parameters) {
            squaredError.put(parameter, 0.0);
        }
        rowSquaredErrorInfos.put(PredictServiceConfig.ROW_SQUARD_ERROR_LIST_MAP_KEY, squaredError);

        predictOnlineInfo.setRowSquaredErrorInfos(projectName, rowSquaredErrorInfos);

    }

}
