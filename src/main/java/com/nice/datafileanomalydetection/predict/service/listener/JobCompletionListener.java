package com.nice.datafileanomalydetection.predict.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;


@Component("jobCompletionListener")
@JobScope
public class JobCompletionListener extends JobExecutionListenerSupport {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();


    @Override
    public void afterJob (JobExecution jobExecution) {
        if (BatchStatus.COMPLETED == jobExecution.getStatus()) {
            logger.info("Batch Job Completed Successfully");
        } else if (BatchStatus.ABANDONED == jobExecution.getStatus() || BatchStatus.FAILED == jobExecution.getStatus()) {
            logger.error(jobExecution.getStatus().toString());
        }

        // TODO - classloader에 올라간 모델 제거가 필요한지 확인. 제거 가능?
    }
}
