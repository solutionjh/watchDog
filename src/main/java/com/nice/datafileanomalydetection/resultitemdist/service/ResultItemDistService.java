package com.nice.datafileanomalydetection.resultitemdist.service;

import com.nice.datafileanomalydetection.resultitemdist.dao.ResultItemDistDao;
import com.nice.datafileanomalydetection.resultitemdist.model.ResultItemDist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class ResultItemDistService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    ResultItemDistDao resultItemDistDao;

    public List<String> getProjectNames () {
        return resultItemDistDao.getProjectNames();
    }

    public List<String> getResultItemDistRegdtim (String projectName) {
        return resultItemDistDao.getResultItemDistRegdtim(projectName);
    }

    public List<ResultItemDist> getProjectItemDistResult (String projectName, String regDtim) {
        return resultItemDistDao.getProjectItemDistResult(projectName, regDtim);
    }

    public List<String> getProjectRegdtimFieldNames (String projectName, String regDtim) {
        return resultItemDistDao.getProjectRegdtimFieldNames(projectName, regDtim);
    }

    public List<ResultItemDist> getProjectRegdtimFieldResult (String projectName, String regDtim, String fieldName) {
        return resultItemDistDao.getProjectRegdtimFieldResult(projectName, regDtim, fieldName);
    }
}
