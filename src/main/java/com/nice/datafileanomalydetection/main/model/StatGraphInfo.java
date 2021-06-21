package com.nice.datafileanomalydetection.main.model;

import org.springframework.stereotype.Component;

@Component("statGraphInfo")
public class StatGraphInfo {

    private String projectName;
    private String dtim;
    private String psi;
    private String car;
    private String chisqcnt;
    private String inputDataCnt;

    public StatGraphInfo() {
    }

    public StatGraphInfo(String projectName, String dtim, String psi, String car, String chisqcnt, String inputDataCnt) {
        this.projectName = projectName;
        this.dtim = dtim;
        this.psi = psi;
        this.car = car;
        this.chisqcnt = chisqcnt;
        this.inputDataCnt = inputDataCnt;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDtim() {
        return dtim;
    }

    public void setDtim(String dtim) {
        this.dtim = dtim;
    }

    public String getPsi() {
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

    public String getChisqcnt () {
        return chisqcnt;
    }

    public void setChisqcnt (String chisqcnt) {
        this.chisqcnt = chisqcnt;
    }

    public String getInputDataCnt () {
        return inputDataCnt;
    }

    public void setInputDataCnt (String inputDataCnt) {
        this.inputDataCnt = inputDataCnt;
    }


}