package com.nice.datafileanomalydetection.predict.model;

import org.springframework.stereotype.Component;

@Component("chisqInfo")
public class ChisqInfo {

    private String projectName;
    private String regDtim;
    private String fieldName;
    private long dof;
    private double chisqStat;
    private double pValue;
    private long newValueCnt;
    private int anomalyRslt;

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

    public long getDof () {
        return dof;
    }

    public void setDof (long dof) {
        this.dof = dof;
    }

    public double getChisqStat () {
        return chisqStat;
    }

    public void setChisqStat (double chisqStat) {
        this.chisqStat = chisqStat;
    }

    public double getpValue () {
        return pValue;
    }

    public void setpValue (double pValue) {
        this.pValue = pValue;
    }

    public long getNewValueCnt () {
        return newValueCnt;
    }

    public void setNewValueCnt (long newValueCnt) {
        this.newValueCnt = newValueCnt;
    }

    public int getAnomalyRslt () {
        return anomalyRslt;
    }

    public void setAnomalyRslt (int anomalyRslt) {
        this.anomalyRslt = anomalyRslt;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("ChisqInfo [");
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
        builder.append("dof=");
        builder.append(dof);
        builder.append(", chisqStat=");
        builder.append(chisqStat);
        builder.append(", pValue=");
        builder.append(pValue);
        builder.append(", newValueCnt=");
        builder.append(newValueCnt);
        builder.append(", anomalyRslt=");
        builder.append(anomalyRslt);
        builder.append("]");
        return builder.toString();
    }

}
