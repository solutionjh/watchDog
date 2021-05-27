package com.nice.datafileanomalydetection.jobexctlog.service;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.nice.datafileanomalydetection.jobexctlog.dao.JobExctLogDao;
import com.nice.datafileanomalydetection.jobexctlog.model.JobExctLog;
import com.nice.datafileanomalydetection.message.ReloadMessageSource;

@Service
public class JobExctLogService{

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    JobExctLogDao jobExctLogDao;
    
    @Autowired
	private ReloadMessageSource message;
    
    public List<JobExctLog> getJobExctLogList (Authentication authentication) throws Exception {   
    	return jobExctLogDao.getJobExctLogList(authentication.getName()); 
    }    
    
    public void deleteJobExctLog (Authentication authentication) throws Exception {   
		 jobExctLogDao.deleteJobExctLog(authentication.getName());
    }    
}
