package com.nice.datafileanomalydetection.predict.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component("makePaddedToNonPadWriteListener")
//@StepScope
public class MakePaddedToNonPadWriteListener implements ItemWriteListener<ConcurrentHashMap> {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private long readSumCount = 0;
    private long totalDataCount;

    public void setReadSumCount (long readSumCount) {
        this.readSumCount = readSumCount;
    }

    public void setTotalDataCount (long totalDataCount) {
        this.totalDataCount = totalDataCount;
    }

    @Override
    public void beforeWrite (List<? extends ConcurrentHashMap> items) {

    }

    @Override
    public void afterWrite (List<? extends ConcurrentHashMap> items) {

        NumberFormat nf = NumberFormat.getNumberInstance();
        readSumCount += items.size();

        logger.info("{} / {} count result success", readSumCount, totalDataCount);
    }

    @Override
    public void onWriteError (Exception exception, List<? extends ConcurrentHashMap> items) {

    }

}
