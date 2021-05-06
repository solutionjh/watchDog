package com.nice.datafileanomalydetection.predict.service.writer;

import com.nice.datafileanomalydetection.predict.model.RowMeanProbInfo;
import com.nice.datafileanomalydetection.predict.model.RowMeanSquaredErrorInfo;
import com.nice.datafileanomalydetection.predict.service.PredictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PredictJsonItemWriterH2<T> implements ItemWriter<T>, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    PredictService predictService;

    private String columnCalculateYN;

    private String squaredErrorListMapKey;

    private String meanSquaredErrorMapKey;

    private String rowMeanProbMapKey;

    private String itemStdedProbMapKey;

    public void setColumnCalculateYN (String columnCalculateYN) {
        this.columnCalculateYN = columnCalculateYN;
    }

    /**
     * 배치의 Map 객체에서 Row의 각 항목별 SquardError 산출 List를 저장한 Key값을 Set
     *
     * @param squaredErrorListMapKey
     */
    public void setSquaredErrorListMapKey (String squaredErrorListMapKey) {
        this.squaredErrorListMapKey = squaredErrorListMapKey;
    }

    /**
     * 배치의 Map 객체에서 MeanSquardError를 저장한 Key값을 Set
     *
     * @param meanSquaredErrorMapKey
     */
    public void setMeanSquaredErrorMapKey (String meanSquaredErrorMapKey) {
        this.meanSquaredErrorMapKey = meanSquaredErrorMapKey;
    }

    // refs #1283 online 이상탐지를 위하여 표준화하여 확률값 계산 (2020-06-24, kty)
    public void setRowMeanProbMapKey (String rowMeanProbMapKey) {
        this.rowMeanProbMapKey = rowMeanProbMapKey;
    }

    public void setItemStdedProbMapKey (String itemStdedProbMapKey) {
        this.itemStdedProbMapKey = itemStdedProbMapKey;
    }

    @Override
    public void write (List<? extends T> items) {

        if (! items.isEmpty()) {
            for (T item : items) {

                Map<String, Object> map = (ConcurrentHashMap<String, Object>) item;

                RowMeanSquaredErrorInfo rowMeanSquardError = (RowMeanSquaredErrorInfo) map.get(this.meanSquaredErrorMapKey);
                Assert.notNull(rowMeanSquardError, this.meanSquaredErrorMapKey + " Object is Empty.");
                //logger.info("rowMeanSquardError in write [{}]", rowMeanSquardError.getMeanSquaredError());
                predictService.insertRowMeanSquardError(rowMeanSquardError);

                // refs #1283 online 이상탐지를 위하여 표준화하여 확률값 계산 (2020-06-24, kty)
                RowMeanProbInfo rowMeanProbability = (RowMeanProbInfo) map.get(this.rowMeanProbMapKey);
                predictService.insertRowMeanProb(rowMeanProbability);

            }
        }
    }

    @Override
    public void afterPropertiesSet () {
        if ("Y".equals(columnCalculateYN)) {
            Assert.state(squaredErrorListMapKey != null, "SquaredErrorListMapKey must be provided");
            Assert.state(itemStdedProbMapKey != null, "ItemStdedProbMapKey must be provided");
        }

        Assert.state(meanSquaredErrorMapKey != null, "MeanSquaredErrorMapKey must be provided");
        Assert.state(rowMeanProbMapKey != null, "RowMeanProbMapKey must be provided");
    }

}
