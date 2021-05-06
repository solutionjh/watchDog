package com.nice.datafileanomalydetection.predict.service.listener;

import com.nice.datafileanomalydetection.predict.model.PredictBatchInfo;
import com.nice.datafileanomalydetection.predict.service.PredictServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Component("predictItemWriterListener")
@StepScope
public class PredictItemWriterListener implements ItemWriteListener<ConcurrentHashMap> {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Value("#{stepExecution}")
    StepExecution stepExecution;
    @Autowired
    PredictBatchInfo predictBatchInfoTemp;
    private long readSumCount = 0;
    @Value("#{jobParameters['totalLineCount']}")
    private final long dataCount = 0;
    @Value("#{jobParameters['columnCalculateYN']}")
    private String columnCalculateYN;

    @Override
    public void beforeWrite (List<? extends ConcurrentHashMap> items) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterWrite (List<? extends ConcurrentHashMap> items) {

        NumberFormat nf = NumberFormat.getNumberInstance();
        readSumCount += items.size();

        logger.info(nf.format(readSumCount) + " / " + nf.format(dataCount) + " count result success");

        ConcurrentHashMap<String, Double> squaredErrors = new ConcurrentHashMap<String, Double>();

        for (ConcurrentHashMap<String, Object> chm : items) {

            ConcurrentHashMap<String, Double> result = (ConcurrentHashMap<String, Double>) chm.get(PredictServiceConfig.ROW_SQUARD_ERROR_LIST_MAP_KEY);
            // 2020-03-30 kty columnCalculateYN 값을 N 으로 체크하면 result.keySet() 에서 발생하는 NullPointerException 회피.
            if ("Y".equals(this.columnCalculateYN)) {

                for (String key : result.keySet()) {
                    double se;
                    try {
                        se = squaredErrors.get(key);
                    } catch (NullPointerException ne) {
                        se = 0.0;
                    }
                    se = se + result.get(key);
                    squaredErrors.put(key, se);
                }
            }
        }
        predictBatchInfoTemp.staticSquaredErrors.add(squaredErrors);

    }

    @Override
    public void onWriteError (Exception exception, List<? extends ConcurrentHashMap> items) {
        // TODO Auto-generated method stub

    }


}
