package com.nice.datafileanomalydetection.online.service;

import com.nice.datafileanomalydetection.online.dao.OnlineDao;
import com.nice.datafileanomalydetection.online.model.OnlineItemResult;
import com.nice.datafileanomalydetection.online.model.OnlineResult;
import com.nice.datafileanomalydetection.online.model.OnlineRowDistResult;
import com.nice.datafileanomalydetection.online.model.OnlineRowResult;
import com.nice.datafileanomalydetection.predict.model.KeyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class OnlineService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    OnlineDao onlineDao;

    public List<String> getResultProjectNames () {
        return onlineDao.getResultProjectNames();
    }

    public List<OnlineResult> getResults () {
        return onlineDao.getResults();
    }

    public List<OnlineResult> getProjectResult (String projectName) {
        return onlineDao.getProjectResult(projectName);
    }

    public List<String> getRowResultProjectNames () {
        return onlineDao.getRowResultProjectNames();
    }

    public List<String> getRowResultRegdtim (String projectName) {
        return onlineDao.getRowResultRegdtim(projectName);
    }

    public List<OnlineRowResult> getProjectRowResult (String projectName) {
        return onlineDao.getProjectRowResult(projectName);
    }

    public List<OnlineRowResult> getProjectRegdtimRowResult (String projectName, String regDtim) {
        return onlineDao.getProjectRegdtimRowResult(projectName, regDtim);
    }

    public List<String> getItemResultProjectNames () {
        return onlineDao.getItemResultProjectNames();
    }

    public List<String> getItemResultRegdtim (String projectName) {
        return onlineDao.getItemResultRegdtim(projectName);
    }

    public List<String> getItemResultFieldName (String projectName, String regDtim) {
        if ("All".equals(regDtim)) {
            return onlineDao.getProjectItemFieldName(projectName);
        }
        return onlineDao.getItemResultFieldName(projectName, regDtim);
    }

    public List<OnlineItemResult> getProjectRegdtimItemResult (String projectName, String regDtim) {
        return onlineDao.getProjectRegdtimItemResult(projectName, regDtim);
    }

    public List<OnlineItemResult> getProjectRegdtimFieldItemResult (String projectName, String regDtim, String fieldName) {
        if ("All".equals(regDtim)) {
            return onlineDao.getProjectFieldItemResult(projectName, fieldName);
        }
        return onlineDao.getProjectRegdtimFieldItemResult(projectName, regDtim, fieldName);
    }

    public List<String> getRowDistProjectNames () {
        return onlineDao.getRowDistProjectNames();
    }

    public List<String> getRowDistProjectRegdtim (String projectName) {
        return onlineDao.getRowDistProjectRegdtim(projectName);
    }

    public List<OnlineRowDistResult> getDevRowDistResult (String projectName) {
        return onlineDao.getDevRowDistResult(projectName);
    }

    public List<OnlineRowDistResult> getTotRowDistResult (String projectName, String regDtim) {
        KeyInfo keyInfo = new KeyInfo();
        keyInfo.setProjectName(projectName);
        keyInfo.setRegDtim(regDtim);

        return onlineDao.selectOnlineRowDistResult(keyInfo);
    }

    public void writeOnlinePredictResult (KeyInfo keyInfo, String resultFilePath) throws IOException {
        List<OnlineResult> onlineResultList = onlineDao.selectOnlinePredictResult(keyInfo);
        Path onlineResultFilePath = Paths.get(resultFilePath + "/Results.wd");
        if (Files.notExists(onlineResultFilePath.getParent())) Files.createDirectory(onlineResultFilePath.getParent());
        if (Files.notExists(onlineResultFilePath)) {
            Files.write(onlineResultFilePath, (onlineResultList.get(0).getHeader() + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE);
        }
        for (OnlineResult onlineResult : onlineResultList) {
            //logger.info("OnlineResult [{}] exists[{}]", onlineResult.toString(),Files.exists(onlineResultFilePath));
            Files.write(onlineResultFilePath, (onlineResult.getResult() + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        }

    }

    public void writeOnlineRowProbStat (KeyInfo keyInfo, String resultFilePath) throws IOException {
        List<OnlineRowResult> onlineRowResultList = onlineDao.selectOnlineRowResult(keyInfo);
        Path onlineRowResultFilePath = Paths.get(resultFilePath + "/RowResults.wd");
        if (Files.notExists(onlineRowResultFilePath.getParent()))
            Files.createDirectory(onlineRowResultFilePath.getParent());
        if (Files.notExists(onlineRowResultFilePath)) {
            Files.write(onlineRowResultFilePath, (onlineRowResultList.get(0).getHeader() + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE);
        }
        for (OnlineRowResult onlineRowResult : onlineRowResultList) {
            //logger.info("OnlineRowResult [{}]", onlineRowResult.toString());
            Files.write(onlineRowResultFilePath, (onlineRowResult.getRowResult() + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        }

    }

    public void writeOnlineItemSEProb (KeyInfo keyInfo, String resultFilePath) throws IOException {
        List<OnlineItemResult> onlineItemResultList = onlineDao.selectOnlineItemResult(keyInfo);
        Path onlineItemResultFilePath = Paths.get(resultFilePath + "/ItemResults.wd");
        if (Files.notExists(onlineItemResultFilePath.getParent()))
            Files.createDirectory(onlineItemResultFilePath.getParent());
        if (Files.notExists(onlineItemResultFilePath)) {
            Files.write(onlineItemResultFilePath, (onlineItemResultList.get(0).getHeader() + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE);
        }
        for (OnlineItemResult onlineItemResult : onlineItemResultList) {
            //logger.info("OnlineItemResult [{}]", onlineItemResult.toString());
            Files.write(onlineItemResultFilePath, (onlineItemResult.getItemResult() + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        }

    }

    public void writeOnlineRowProbDist (KeyInfo keyInfo, String resultFilePath) throws IOException {
        List<OnlineRowDistResult> onlineRowDistResultList = onlineDao.selectOnlineRowDistResult(keyInfo);
        Path onlineRowDistResultFilePath = Paths.get(resultFilePath + "/RowDistResults.wd");
        if (Files.notExists(onlineRowDistResultFilePath.getParent()))
            Files.createDirectory(onlineRowDistResultFilePath.getParent());
        if (Files.notExists(onlineRowDistResultFilePath)) {
            Files.write(onlineRowDistResultFilePath, (onlineRowDistResultList.get(0).getHeader() + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE);
        }
        for (OnlineRowDistResult onlineRowDistResult : onlineRowDistResultList) {
            //logger.info("OnlineRowDistResult [{}]", onlineRowDistResult.toString());
            Files.write(onlineRowDistResultFilePath, (onlineRowDistResult.getRowDistResult() + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        }
    }

}
