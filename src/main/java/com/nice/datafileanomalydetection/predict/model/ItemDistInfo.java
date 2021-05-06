package com.nice.datafileanomalydetection.predict.model;

import org.springframework.stereotype.Component;

@Component("itemDistInfo")
public class ItemDistInfo {

    private String projectName;
    private String regDtim;
    private String fieldName;
    private String itemValue;
    private long itemCount;
    private double itemProb;

    private long totalDataCnt;

    public long getTotalDataCnt () {
        return totalDataCnt;
    }

    public void setTotalDataCnt (long totalDataCnt) {
        this.totalDataCnt = totalDataCnt;
    }

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

    public long getItemCount () {
        return itemCount;
    }

    public void setItemCount (long itemCount) {
        this.itemCount = itemCount;
    }

    public double getItemProb () {
        return itemProb;
    }

    public void setItemProb (double itemProb) {
        this.itemProb = itemProb;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("ItemDistInfo [");
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
        if (fieldName != null) {
            builder.append("fieldName=");
            builder.append(fieldName);
            builder.append(", ");
        }
        if (itemValue != null) {
            builder.append("itemValue=");
            builder.append(itemValue);
            builder.append(", ");
        }
        builder.append("itemCount=");
        builder.append(itemCount);
        builder.append(", itemProb=");
        builder.append(itemProb);
        builder.append(", totalDataCnt=");
        builder.append(totalDataCnt);
        builder.append("]");
        return builder.toString();
    }

}
