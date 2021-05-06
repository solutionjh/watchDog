package com.nice.datafileanomalydetection.result.model;

import org.springframework.stereotype.Component;

@Component("result")
public class Result {

    private String projectName;
    private String regDtim;
    private String jobId;
    private String jobGb;
    private String inputFilename;
    private String columnCalulateyn;
    private String inputDatacnt;
    private String endDtim;
    private String psi;
    private String car;
    private String chisqCnt;
    private String comment;

    public String getProjectName () {
        return projectName;
    }

    public void setProjectName (String projectName) {
        this.projectName = projectName;
    }

    public String getRegDtim () {
        return regDtim;
    }

    public void setRegDtim (String regDtim) {
        this.regDtim = regDtim;
    }

    public String getJobId () {
        return jobId;
    }

    public void setJobId (String jobId) {
        this.jobId = jobId;
    }

    public String getJobGb () {
        return jobGb;
    }

    public void setJobGb (String jobGb) {
        this.jobGb = jobGb;
    }

    public String getInputFilename () {
        return inputFilename;
    }

    public void setInputFilename (String inputFilename) {
        this.inputFilename = inputFilename;
    }

    public String getColumnCalulateyn () {
        return columnCalulateyn;
    }

    public void setColumnCalulateyn (String columnCalulateyn) {
        this.columnCalulateyn = columnCalulateyn;
    }

    public String getInputDatacnt () {
        return inputDatacnt;
    }

    public void setInputDatacnt (String inputDatacnt) {
        this.inputDatacnt = inputDatacnt;
    }

    public String getEndDtim () {
        return endDtim;
    }

    public void setEndDtim (String endDtim) {
        this.endDtim = endDtim;
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

    public String getComment () {
        return comment;
    }

    public void setComment (String comment) {
        this.comment = comment;
    }

    public String getChisqCnt () {
        return chisqCnt;
    }

    public void setChisqCnt (String chisqCnt) {
        this.chisqCnt = chisqCnt;
    }


}
