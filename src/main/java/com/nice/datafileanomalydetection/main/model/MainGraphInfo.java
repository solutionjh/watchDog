package com.nice.datafileanomalydetection.main.model;

import org.springframework.stereotype.Component;

@Component("mainGraphInfo")
public class MainGraphInfo {

    private String projectName;
    private String dtim;
    private String anomalyCnt;
    private String normalCnt;

    public MainGraphInfo() {
    }

    public MainGraphInfo(String projectName, String dtim, String anomalyCnt, String normalCnt) {
        this.projectName = projectName;
        this.dtim = dtim;
        this.anomalyCnt = anomalyCnt;
        this.normalCnt = normalCnt;
    }

    public String getAnomalyCnt() {
        return anomalyCnt;
    }

    public void setAnomalyCnt(String anomalyCnt) {
        this.anomalyCnt = anomalyCnt;
    }

    public String getNormalCnt() {
        return normalCnt;
    }

    public void setNormalCnt (String normalCnt) {
        this.normalCnt = normalCnt;
    }

    public String getDtim () {
        return dtim;
    }

    public void setDtim (String dtim) {
        this.dtim = dtim;
    }

}