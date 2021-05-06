package com.nice.datafileanomalydetection.main.model;

import org.springframework.stereotype.Component;

@Component("statGraphInfo")
public class StatGraphInfo {

    private String dtim;
    private String psi;
    private String car;
    private String chisqcnt;
    private String inputDataCnt;

    public String getDtim () {
        return dtim;
    }

    public void setDtim (String dtim) {
        this.dtim = dtim;
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
