package com.nice.datafileanomalydetection.main.model;

import org.springframework.stereotype.Component;

@Component("mainItemInfo")
public class MainItemInfo {

    private String fieldName;
    private String changeRate;
    private String detectedCnt;

    public String getFieldName () {
        return fieldName;
    }

    public void setFieldName (String fieldName) {
        this.fieldName = fieldName;
    }

    public String getChangeRate () {
        return changeRate;
    }

    public void setChangeRate (String changeRate) {
        this.changeRate = changeRate;
    }

    public String getDetectedCnt () {
        return detectedCnt;
    }

    public void setDetectedCnt (String detectedCnt) {
        this.detectedCnt = detectedCnt;
    }


}
