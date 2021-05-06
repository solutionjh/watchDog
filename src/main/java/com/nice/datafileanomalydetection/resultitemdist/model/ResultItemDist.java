package com.nice.datafileanomalydetection.resultitemdist.model;

import org.springframework.stereotype.Component;

@Component("resultitemdist")
public class ResultItemDist {

    private String fieldName;
    private String itemValue;
    private String devProbability;
    private String nowProbability;
    private String diff;

    public String getFieldName () {
        return fieldName;
    }

    public void setFieldName (String fieldName) {
        this.fieldName = fieldName;
    }

    public String getItemValue () {
        return itemValue;
    }

    public void setItemValue (String itemValue) {
        this.itemValue = itemValue;
    }

    public String getDevProbability () {
        return devProbability;
    }

    public void setDevProbability (String devProbability) {
        this.devProbability = devProbability;
    }

    public String getNowProbability () {
        return nowProbability;
    }

    public void setNowProbability (String nowProbability) {
        this.nowProbability = nowProbability;
    }

    public String getDiff () {
        return diff;
    }

    public void setDiff (String diff) {
        this.diff = diff;
    }


}
