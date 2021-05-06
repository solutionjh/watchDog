package com.nice.datafileanomalydetection.predict.service.processor;

import com.nice.datafileanomalydetection.predict.model.PredictOnlineInfo;
import com.nice.datafileanomalydetection.predict.model.RowMeanProbInfo;
import com.nice.datafileanomalydetection.predict.model.RowMeanSquaredErrorInfo;
import com.nice.datafileanomalydetection.predict.service.PredictServiceConfig;
import com.nice.datafileanomalydetection.predict.service.modelloader.AnomalyDetectionModelLoader;
import com.nice.datafileanomalydetection.predict.util.PredictCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component("predictJSONProcess")
@StepScope
public class PredictJSONProcessService implements ItemProcessor<Map<String, Object>, Map<String, Object>> {

    protected static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("#{jobParameters['keyItems']}")
    private String keyItems;
    @Value("#{jobParameters['targetKeys']}")
    private String targetKeys;
    @Value("#{jobParameters['columnCalculateYN']}")
    private String columnCalculateYN;
    @Value("#{jobParameters['projectName']}")
    private String projectName;
    @Value("#{jobParameters['regDtim']}")
    private String regDtim;

    @Value("#{stepExecution}")
    private StepExecution stepExecution;

    @Autowired
    private PredictOnlineInfo predictOnlineInfo;

    /**
     * 외부제공용 메소드 수정, kjh, 2020.05.06
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> process (Map<String, Object> map) throws Exception {
        // TODO 응답 Map 샘플데이터로 셋팅하기! - kjh, 2021.05.06
        return map;

    }
}
