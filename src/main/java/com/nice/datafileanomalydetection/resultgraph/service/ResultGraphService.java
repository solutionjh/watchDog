/*
2020.03.18 kwb 
- 기존 ResultItemService.java 활용
*/

package com.nice.datafileanomalydetection.resultgraph.service;

import com.nice.datafileanomalydetection.resultgraph.dao.ResultGraphDao;
import com.nice.datafileanomalydetection.resultgraph.model.ResultGraphModelPoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class ResultGraphService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    ResultGraphDao resultGraphDao;

    public List<String> getProjectNames () {
        return resultGraphDao.getProjectNames();
    }

    public List<String> getResultRegdtim (String projectName) {
        return resultGraphDao.getResultRegdtim(projectName);
    }

    // 기준시점 데이터 리스트와 현재시점 데이터 리스트를 ResultGraphModelPoints.java에 세팅
    public ResultGraphModelPoints getResultGraphModelPoints (String projectName, String regdtim) {

        ResultGraphModelPoints resultGraphModelPoints = new ResultGraphModelPoints();

        resultGraphModelPoints.setDevMseNRatioList(resultGraphDao.getDevMseNRatioList(projectName));
        resultGraphModelPoints.setNowMseNRatioList(resultGraphDao.getNowMseNRatioList(projectName, regdtim));

        return resultGraphModelPoints;
    }

}
