package com.nice.datafileanomalydetection.predict.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nice.datafileanomalydetection.predict.model.PredictInputInfo;

import io.swagger.annotations.ApiOperation;

@EnableBatchProcessing
@RestController
public class extraController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    
    @ApiOperation(value = "test")
    @PutMapping(value = "/extra/predictmulti")
    public String test (@RequestBody List<PredictInputInfo> predictInputInfos) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
        String response = "";
        System.out.println("===============================");
        return response;
    }

    
}
