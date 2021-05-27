package com.nice.datafileanomalydetection.resultitem.service;

import com.nice.datafileanomalydetection.resultitem.dao.ResultItemDao;
import com.nice.datafileanomalydetection.resultitem.model.ResultItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class ResultItemService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    ResultItemDao resultItemDao;

    public List<String> getResultItemRegdtim (String projectName) {
        return resultItemDao.getResultItemRegdtim(projectName);
    }
    
    public List<ResultItem> getProjectItemResult (String projectName, String regdtim, String changeRate) {
    	return resultItemDao.getProjectItemResult(projectName, regdtim, changeRate);
    }

}
