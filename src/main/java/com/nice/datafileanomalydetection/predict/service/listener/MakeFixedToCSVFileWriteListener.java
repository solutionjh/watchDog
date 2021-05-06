package com.nice.datafileanomalydetection.predict.service.listener;

import com.nice.datafileanomalydetection.predict.model.MakeCSVInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Component("makeFixedToCSVFileWriteListener")
public class MakeFixedToCSVFileWriteListener implements ItemWriteListener<ConcurrentHashMap> {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    MakeCSVInfo makeCSVInfo;
    private long readSumCount = 0;

    public void setReadSumCount (long readSumCount) {
        this.readSumCount = readSumCount;
    }

    @Override
    public void beforeWrite (List<? extends ConcurrentHashMap> items) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterWrite (List<? extends ConcurrentHashMap> items) {

        NumberFormat nf = NumberFormat.getNumberInstance();
        readSumCount += items.size();

        logger.info(nf.format(readSumCount) + " / " + nf.format(makeCSVInfo.getInputDataCount()) + " count result success");
    }

    @Override
    public void onWriteError (Exception exception, List<? extends ConcurrentHashMap> items) {
        // TODO Auto-generated method stub

    }


}
