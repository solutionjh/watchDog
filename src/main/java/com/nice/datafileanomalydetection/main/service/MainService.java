package com.nice.datafileanomalydetection.main.service;

import com.nice.datafileanomalydetection.main.dao.MainDao;
import com.nice.datafileanomalydetection.main.model.MainGraphInfo;
import com.nice.datafileanomalydetection.main.model.MainItemInfo;
import com.nice.datafileanomalydetection.main.model.StatGraphInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MainService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    MainDao mainDao;

    public List<String> getProjectNames () {
        return mainDao.getProjectNames();
    }

    public List<String> getRegDtim () {
        return mainDao.getRegDtim();
    }

    public List<String> getProjectRegDtim (String projectName) {
        return mainDao.getProjectRegDtim(projectName);
    }

    public List<MainGraphInfo> getAnomalyCount (String fromDtim) {
        return mainDao.getAnomalyCount(fromDtim);
    }

    public List<MainGraphInfo> getProjectAnomalyCount (String projectName, String fromDtim) {
        return mainDao.getProjectAnomalyCount(projectName, fromDtim);
    }

    public List<StatGraphInfo> getStats (String fromDtim) {
        return mainDao.getStats(fromDtim);
    }

    public List<StatGraphInfo> getProjectStats (String projectName, String fromDtim) {
        return mainDao.getProjectStats(projectName, fromDtim);
    }

    public List<MainItemInfo> getAnomalyItemChangeRate (String projectName, String fromDtim) {
        return mainDao.getAnomalyItemChangeRate(projectName, fromDtim);
    }

    public List<MainItemInfo> getAnomalyItemDetectCount (String projectName, String fromDtim) {
        return mainDao.getAnomalyItemDetectCount(projectName, fromDtim);
    }

    public List<String> getAllProjectNames () {
        String[] projectTables = new String[]{"PREDICT_JOB", "DEV_COL_MEANSQUAREDERROR", "DEV_ITEM_DISTRIBUTION", "DEV_MEANSQUAREDERROR",
                "MODEL_DATA_SUMMARY", "PREDICT_CHISQ", "PREDICT_DATA_SUMMARY", "TOT_COL_MEANSQUAREDERROR",
                "TOT_ITEM_DISTRIBUTION", "TOT_MEANSQUAREDERROR", "PREDICT_ROW_MEANSQUAREDERROR", "PREDICT_ROW_SQUAREDERROR"};

        List<String> allProjectNames = new ArrayList<String>();
        for (String tableName : projectTables) {
            allProjectNames.addAll(mainDao.getProjectNamesFromTable(tableName));
        }

        Set<String> allProjectNameSet = new HashSet<>(allProjectNames);
        allProjectNames.clear();
        allProjectNames.addAll(allProjectNameSet);
        allProjectNames.sort(null);

        return allProjectNames;
    }

    public int deleteProjectData (String projectName) {
        int resultInt = 0;
        String[] projectTables = new String[]{"PREDICT_JOB", "DEV_COL_MEANSQUAREDERROR", "DEV_ITEM_DISTRIBUTION", "DEV_MEANSQUAREDERROR",
                "MODEL_DATA_SUMMARY", "PREDICT_CHISQ", "PREDICT_DATA_SUMMARY", "TOT_COL_MEANSQUAREDERROR",
                "TOT_ITEM_DISTRIBUTION", "TOT_MEANSQUAREDERROR", "PREDICT_ROW_MEANSQUAREDERROR", "PREDICT_ROW_SQUAREDERROR"};
        for (String tableName : projectTables) {
            resultInt += mainDao.deleteProjectData(projectName, tableName);
        }

        return resultInt;

    }

    public int cleanBatchTable () {
        int resultInt = 0;
        String[] batchInfoTables = new String[]{"BATCH_JOB_EXECUTION", "BATCH_JOB_EXECUTION_CONTEXT", "BATCH_JOB_EXECUTION_PARAMS",
                "BATCH_JOB_INSTANCE", "BATCH_STEP_EXECUTION", "BATCH_STEP_EXECUTION_CONTEXT"};

        for (String tableName : batchInfoTables) {
            resultInt += mainDao.truncateTableData(tableName);
        }

        return resultInt;
    }
}
