package com.nice.datafileanomalydetection.online.model;

import org.springframework.stereotype.Component;

@Component
public class OnlineItemResult extends OnlineKey {

    private String fieldName;
    private double meanProb;

    public String getFieldName () {
        return fieldName;
    }

    public void setFieldName (String fieldName) {
        this.fieldName = fieldName;
    }

    public double getMeanProb () {
        return meanProb;
    }

    public void setMeanProb (double meanProb) {
        this.meanProb = meanProb;
    }

    @Override
    public String toString () {
        return "OnlineItemResult [projectName=" + getProjectName() + ", regDtim=" + getRegDtim() + ", fieldName=" + fieldName + ", meanProb=" + meanProb + "]";
    }

    public String getItemResult () {
        return getProjectName() + "," + getPkgId() + "," + getSnstId() + "," + getInputDate() + "," + getRegDtim() + "," + fieldName + "," + meanProb;
    }

    public String getHeader () {
        return String.join(",", new String[]{"PROJECTNAME", "PKGID", "SNSTID", "INPUTDATE", "REGDTIM", "FIELDNAME", "MEANPROBABILITY"});
    }

}
