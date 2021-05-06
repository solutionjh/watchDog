package com.nice.datafileanomalydetection.predict.service.listener;

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
import java.text.NumberFormat;

@Component("makePaddedToNonPadListener")
@StepScope
public class MakePaddedToNonPadListener extends StepExecutionListenerSupport {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    MakePaddedToNonPadWriteListener makePaddedToNonPadWriteListener;


    @Value("#{jobParameters['inputDataFile']}")
    private String inputDataFile;

    @Value("#{jobParameters['outputDataFile']}")
    private String outputDataFile;

    @Value("#{jobParameters['regDtim']}")
    private String regDtim;

    @Value("#{jobParameters['inputItemList']}")
    private String inputItemList;

    @Value("#{jobParameters['totalLineCount']}")
    private long totalLineCount;


    @Override
    public ExitStatus afterStep (StepExecution stepExecution) {
        NumberFormat nf = NumberFormat.getNumberInstance();

        logger.info("Make NonPad Listener {} count read", nf.format(stepExecution.getReadCount()));
        logger.info("Make NonPad Listener {} count write", nf.format(stepExecution.getWriteCount()));

        return null;
    }

    @Override
    public void beforeStep (StepExecution stepExecution) {
        logger.info("Step Execution Summary [{}]", stepExecution.getSummary());
        logger.info("Available Processors: {}", Runtime.getRuntime().availableProcessors());

        try {
            makePaddedToNonPadWriteListener.setReadSumCount(0);
            makePaddedToNonPadWriteListener.setTotalDataCount(totalLineCount);


        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }


}
