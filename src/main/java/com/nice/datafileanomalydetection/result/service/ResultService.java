package com.nice.datafileanomalydetection.result.service;

import com.nice.datafileanomalydetection.result.dao.ResultDao;
import com.nice.datafileanomalydetection.result.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class ResultService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    ResultDao resultDao;

    public List<String> getProjectNames () {
        return resultDao.getProjectNames();
    }

    public List<Result> getResults () {
        return resultDao.getResults();
    }

    public List<Result> getProjectResult (String projectName) {
        return resultDao.getProjectResult(projectName);
    }

}
