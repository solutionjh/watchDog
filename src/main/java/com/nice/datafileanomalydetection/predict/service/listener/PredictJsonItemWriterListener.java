package com.nice.datafileanomalydetection.predict.service.listener;

import com.nice.datafileanomalydetection.predict.model.PredictOnlineInfo;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component("predictJsonItemWriterListener")
@StepScope
public class PredictJsonItemWriterListener implements ItemWriteListener<ConcurrentHashMap> {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Value("#{stepExecution}")
    StepExecution stepExecution;
    @Autowired
    PredictOnlineInfo predictOnlineInfo;
    private long writeCnt = 0;
    @Value("#{jobParameters['columnCalculateYN']}")
    private String columnCalculateYN;
    @Value("#{jobParameters['projectName']}")
    private String projectName;

    @Override
    public void beforeWrite (List<? extends ConcurrentHashMap> items) {

    }

    @Override
    public void afterWrite (List<? extends ConcurrentHashMap> items) {

        NumberFormat nf = NumberFormat.getNumberInstance();
        writeCnt += items.size();

        logger.info("{} count json result writed", writeCnt);

        ConcurrentHashMap<String, Double> squaredErrors = new ConcurrentHashMap<String, Double>();
        // refs #1283 online 이상탐지를 위하여 표준화하여 항목별 확률값 계산 (2020-06-26, kty)
        ConcurrentHashMap<String, Double> itemSEProbs = new ConcurrentHashMap<String, Double>();

        for (ConcurrentHashMap<String, Object> chm : items) {

            ConcurrentHashMap<String, Double> result = (ConcurrentHashMap<String, Double>) chm.get(PredictServiceConfig.ROW_SQUARD_ERROR_LIST_MAP_KEY);
            // refs #1283 online 이상탐지를 위하여 표준화하여 항목별 확률값 계산 (2020-06-26, kty)
            ConcurrentHashMap<String, Double> itemSEProbResult = (ConcurrentHashMap<String, Double>) chm.get(PredictServiceConfig.ITEM_STDED_PROB_MAP_KEY);

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

                // refs #1283 online 이상탐지를 위하여 표준화하여 항목별 확률값 계산 (2020-06-26, kty)
                for (Map.Entry<String, Double> elem : itemSEProbResult.entrySet()) {
                    double itemSE;
                    String itemName = elem.getKey();
                    try {
                        itemSE = itemSEProbs.get(itemName);
                    } catch (NullPointerException ne) {
                        itemSE = 0.0;
                    }
                    itemSE += elem.getValue();
                    itemSEProbs.put(itemName, itemSE);
                }
            }
        }

        //staticSquaredErrors
        predictOnlineInfo.getStaticSquaredErrors(projectName).add(squaredErrors);
        //logger.info("Static Squared Erros in WriterListener afterWrite [{}]", staticSquaredErrors.toString());

        // refs #1283 online 이상탐지를 위하여 표준화하여 항목별 확률값 계산 (2020-06-26, kty)
        predictOnlineInfo.getStaticItemSEProbs(projectName).add(itemSEProbs);

        // afterWrite 를 거친 데이터 건수 저장(데이터 건수와 다른지 확인 용도)
        predictOnlineInfo.setWriteCnt(projectName, writeCnt);

    }

    @Override
    public void onWriteError (Exception exception, List<? extends ConcurrentHashMap> items) {

    }

}
