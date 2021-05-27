package com.nice.datafileanomalydetection.jobexctlog.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nice.datafileanomalydetection.jobexctlog.model.JobExctLog;
import com.nice.datafileanomalydetection.jobexctlog.service.JobExctLogService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping(value = {"/api/jobExctLog"})
public class JobExctLogController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    JobExctLogService jobExctLogService;
    
    @GetMapping(value = "/getList")
	@ApiOperation(value = "데이터 이상감지 실행완료 목록 조회")
	public List<JobExctLog> getJobExctLogList(HttpServletRequest request, Authentication authentication) throws Exception {
		return jobExctLogService.getJobExctLogList(authentication);
	}
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "데이터 이상감지 실행완료 목록 삭제")
    public void deletetJobExctLog(HttpServletRequest request, Authentication authentication) throws Exception {
    	 jobExctLogService.deleteJobExctLog(authentication);
    }
}
