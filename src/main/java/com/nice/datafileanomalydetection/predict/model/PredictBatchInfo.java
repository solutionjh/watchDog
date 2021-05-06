package com.nice.datafileanomalydetection.predict.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component("predictBatchInfo")
public class PredictBatchInfo {

    public List staticSquaredErrors = new ArrayList();
    private String projectName;
    private String regDtim;
    private String jobId;
    private String jobGb;
    private String inputDataFile;
    private String inputHeaderFile;
    private long inputDataCount;
    private String inputItemList;
    private String endDtim;
    private String comment;
    private String psi;
    private String car;
    private String modelClassName;
    private String columnCalculateYN;
    private ConcurrentHashMap<String, ConcurrentHashMap<String, Double>> rowSquaredErrorInfo;
    private String chiSqCnt;
    private String carThreshold;

    public String getJobGb () {
        return jobGb;
    }

    public void setJobGb (String jobGb) {
        this.jobGb = jobGb;
    }

    public String getPsi () {
        return psi;
    }

    public void setPsi (String psi) {
        this.psi = psi;
    }

    public String getCar () {
        return car;
    }

    public void setCar (String car) {
        this.car = car;
    }

    public String getProjectName () {
        return projectName;
    }

    public void setProjectName (String projectName) {
        this.projectName = projectName;
    }

    public String getInputDataFile () {
        return inputDataFile;
    }

    public void setInputDataFile (String inputDataFile) {
        this.inputDataFile = inputDataFile;
    }

    public String getInputHeaderFile () {
        return inputHeaderFile;
    }

    public void setInputHeaderFile (String inputHeaderFile) {
        this.inputHeaderFile = inputHeaderFile;
    }

    public long getInputDataCount () {
        return inputDataCount;
    }

    public void setInputDataCount (long inputDateCount) {
        this.inputDataCount = inputDateCount;
    }

    public String getRegDtim () {
        return regDtim;
    }

    public void setRegDtim (String regDtim) {
        this.regDtim = regDtim;
    }

    public String getEndDtim () {
        return endDtim;
    }

    public void setEndDtim (String endDtim) {
        this.endDtim = endDtim;
    }

    public String getJobId () {
        return jobId;
    }

    public void setJobId (String jobId) {
        this.jobId = jobId;
    }

    public String getInputItemList () {
        return inputItemList;
    }

    public void setInputItemList (String inputItemList) {
        this.inputItemList = inputItemList;
    }

    public String getComment () {
        return comment;
    }

    public void setComment (String comment) {
        this.comment = comment;
    }

    public String getModelClassName () {
        return modelClassName;
    }

    public void setModelClassName (String modelClassName) {
        this.modelClassName = modelClassName;
    }

    public String getColumnCalculateYN () {
        return columnCalculateYN;
    }

    public void setColumnCalculateYN (String columnCalculateYN) {
        this.columnCalculateYN = columnCalculateYN;
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, Double>> getRowSquaredErrorInfo () {
        return rowSquaredErrorInfo;
    }

    public void setRowSquaredErrorInfo (ConcurrentHashMap<String, ConcurrentHashMap<String, Double>> rowSquaredErrorInfo) {
        this.rowSquaredErrorInfo = rowSquaredErrorInfo;
    }

    public String getChiSqCnt () {
        return chiSqCnt;
    }

    public void setChiSqCnt (String chiSqCnt) {
        this.chiSqCnt = chiSqCnt;
    }

    public String getCarThreshold () {
        return carThreshold;
    }

    public void setCarThreshold (String carThreshold) {
        this.carThreshold = carThreshold;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("PredictBatchInfo [");
        if (projectName != null) {
            builder.append("projectName=");
            builder.append(projectName);
            builder.append(", ");
        }
        if (regDtim != null) {
            builder.append("regDtim=");
            builder.append(regDtim);
            builder.append(", ");
        }
        if (jobId != null) {
            builder.append("jobId=");
            builder.append(jobId);
            builder.append(", ");
        }
        if (jobGb != null) {
            builder.append("jobGb=");
            builder.append(jobGb);
            builder.append(", ");
        }
        if (inputDataFile != null) {
            builder.append("inputDataFile=");
            builder.append(inputDataFile);
            builder.append(", ");
        }
        if (inputHeaderFile != null) {
            builder.append("inputHeaderFile=");
            builder.append(inputHeaderFile);
            builder.append(", ");
        }
        builder.append("inputDataCount=");
        builder.append(inputDataCount);
        builder.append(", ");
        if (inputItemList != null) {
            builder.append("inputItemList=");
            builder.append(inputItemList);
            builder.append(", ");
        }
        if (endDtim != null) {
            builder.append("endDtim=");
            builder.append(endDtim);
            builder.append(", ");
        }
        if (comment != null) {
            builder.append("comment=");
            builder.append(comment);
            builder.append(", ");
        }
        if (psi != null) {
            builder.append("psi=");
            builder.append(psi);
            builder.append(", ");
        }
        if (car != null) {
            builder.append("car=");
            builder.append(car);
            builder.append(", ");
        }
        if (modelClassName != null) {
            builder.append("modelClassName=");
            builder.append(modelClassName);
            builder.append(", ");
        }
        if (columnCalculateYN != null) {
            builder.append("columnCalculateYN=");
            builder.append(columnCalculateYN);
            builder.append(", ");
        }
        if (rowSquaredErrorInfo != null) {
            builder.append("rowSquaredErrorInfo=");
            builder.append(rowSquaredErrorInfo);
            builder.append(", ");
        }
        if (chiSqCnt != null) {
            builder.append("chiSqCnt=");
            builder.append(chiSqCnt);
            builder.append(", ");
        }
        if (carThreshold != null) {
            builder.append("carThreshold=");
            builder.append(carThreshold);
            builder.append(", ");
        }
        if (staticSquaredErrors != null) {
            builder.append("staticSquaredErrors=");
            builder.append(staticSquaredErrors);
        }
        builder.append("]");
        return builder.toString();
    }

}
