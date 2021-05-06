package com.nice.datafileanomalydetection.predict.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("trainFromLogInfo")
public class TrainFromLogInfo extends KeyInfo {

    private String logFile;
    private List<String> logFileList;
    private String keyItems;
    private String targetKeys;
    // 항목 이상 기준치
    private String anomalyCarCnt;
    private String columnCalculateYN;

    public List<String> getLogFileList() {
        return logFileList;
    }

    public void setLogFileList(List<String> logFileList) {
        this.logFileList = logFileList;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public String getKeyItems() {
        return keyItems;
    }

    public void setKeyItems(String keyItems) {
        this.keyItems = keyItems;
    }

    public String getTargetKeys() {
        return targetKeys;
    }

    public void setTargetKeys(String targetKeys) {
        this.targetKeys = targetKeys;
    }

    public String getAnomalyCarCnt() {
        return anomalyCarCnt;
    }

    public void setAnomalyCarCnt(String anomalyCarCnt) {
        this.anomalyCarCnt = anomalyCarCnt;
    }

    public String getColumnCalculateYN() {
        return columnCalculateYN;
    }

    public void setColumnCalculateYN(String columnCalculateYN) {
        this.columnCalculateYN = columnCalculateYN;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TrainFromLogInfo [");
        if (logFile != null) {
            builder.append("logFile=");
            builder.append(logFile);
            builder.append(", ");
        }
        if (logFileList != null) {
            builder.append("logFileList=");
            builder.append(logFileList);
            builder.append(", ");
        }
        if (keyItems != null) {
            builder.append("keyItems=");
            builder.append(keyItems);
            builder.append(", ");
        }
        if (targetKeys != null) {
            builder.append("targetKeys=");
            builder.append(targetKeys);
            builder.append(", ");
        }
        if (anomalyCarCnt != null) {
            builder.append("anomalyCarCnt=");
            builder.append(anomalyCarCnt);
            builder.append(", ");
        }
        if (columnCalculateYN != null) {
            builder.append("columnCalculateYN=");
            builder.append(columnCalculateYN);
        }
        builder.append("]");
        return builder.toString();
    }

}
