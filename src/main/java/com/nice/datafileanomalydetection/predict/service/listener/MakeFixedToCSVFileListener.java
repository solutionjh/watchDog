package com.nice.datafileanomalydetection.predict.service.listener;

import com.nice.datafileanomalydetection.predict.model.MakeCSVInfo;
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


@Component("makeFixedToCSVFileListener")
@StepScope
public class MakeFixedToCSVFileListener extends StepExecutionListenerSupport {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // autowired 여도 다중 병렬 실행이 되는지 체크 필요
    @Autowired
    MakeFixedToCSVFileWriteListener makeFixedToCSVFileWriteListener;

    @Autowired
    MakeCSVInfo makeCSVInfo;

    @Value("#{jobParameters['inputDataFile']}")
    private String inputDataFile;

    @Value("#{jobParameters['inputHeaderFile']}")
    private String inputHeaderFile;

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

        logger.info("{} count read", nf.format(stepExecution.getReadCount()));
        logger.info("{} count write", nf.format(stepExecution.getWriteCount()));

        return null;
    }

    @Override
    public void beforeStep (StepExecution stepExecution) {
        logger.info(stepExecution.getSummary());
        logger.info("availableProcessors: {}", Runtime.getRuntime().availableProcessors());

        try {
            makeFixedToCSVFileWriteListener.setReadSumCount(0);

            makeCSVInfo.setInputDataCount(totalLineCount);
            makeCSVInfo.setInputItemList(inputItemList);

        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }

    }

}
