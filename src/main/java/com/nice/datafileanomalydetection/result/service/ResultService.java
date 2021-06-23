package com.nice.datafileanomalydetection.result.service;

import com.nice.datafileanomalydetection.result.dao.ResultDao;
import com.nice.datafileanomalydetection.result.model.ItemAnomalyLevel;
import com.nice.datafileanomalydetection.result.model.Result;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<String> getFileName(String projectName, String regDtim) {
        List<String> fileNames = resultDao.getFileName(projectName, regDtim);
        return fileNames.stream().map(filePath -> FilenameUtils.getName(filePath)).collect(Collectors.toList());
    }

    public Map<String, String> getAnomalyIndex(String projectName, String regDtim) {
        Map<String, Object> resultObjectMap = resultDao.getAnomalyIndex(projectName, regDtim);
        Map<String, String> anoamlyIndexMap = new HashMap<>();
        resultObjectMap.forEach((key, value) -> anoamlyIndexMap.put(key, String.valueOf(value)));

        return anoamlyIndexMap;
    }

    public List<ItemAnomalyLevel> getItemAnomalyLevels(String projectName, String regDtim) {
        String formatGb = getPredictType(projectName);
        List<ItemAnomalyLevel> itemAnomalyLevelList = resultDao.getItemAnomalyLevels(projectName, regDtim, formatGb);
        Collections.sort(itemAnomalyLevelList);

        itemAnomalyLevelList.stream().forEach(item -> {
            if ("batch".equals(formatGb) && item.getItemAnomalyLevel() > 50.0) {
                item.setIsAnomaly("1");
            } else if ("online".equals(formatGb) && item.getItemAnomalyLevel() > 65.0) {
                item.setIsAnomaly("1");
            } else {
                item.setIsAnomaly("0");
            }
        });

        return new ArrayList<>(itemAnomalyLevelList.subList(0, 6));
    }

    public String getPredictType(String projectName) {
        projectName = FilenameUtils.getBaseName(projectName);
        String propertiesFilePath = FilenameUtils.concat(System.getProperty("user.dir"), "properties/"+projectName+".properties");
        String formatGb = "";
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(propertiesFilePath));
            formatGb = properties.getProperty("formatGb", "batch");
        } catch(IOException e) {
            logger.error("Properties File {} does not exist", propertiesFilePath);
        }

        return formatGb;
    }

}
