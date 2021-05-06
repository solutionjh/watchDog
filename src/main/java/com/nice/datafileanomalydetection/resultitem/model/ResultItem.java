package com.nice.datafileanomalydetection.resultitem.model;

import org.springframework.stereotype.Component;

@Component("resultitem")
public class ResultItem {

    private String fieldName;
    private String devMse;
    private String nowMse;
    private String jobGb;
    private String changeRate;

    public String getFieldName () {
        return fieldName;
    }

    public void setFieldName (String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDevMse () {
        return devMse;
    }

    public void setDevMse (String devMse) {
        this.devMse = devMse;
    }

    public String getNowMse () {
        return nowMse;
    }

    public void setNowMse (String nowMse) {
        this.nowMse = nowMse;
    }

    public String getJobGb () {
        return jobGb;
    }

    public void setJobGb (String jobGb) {
        this.jobGb = jobGb;
    }

    public String getChangeRate () {
        return changeRate;
    }

    public void setChangeRate (String changeRate) {
        this.changeRate = changeRate;
    }

}
