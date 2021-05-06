package com.nice.datafileanomalydetection.predict.model;

import org.springframework.stereotype.Component;

@Component
public class KeyInfo {

    private String projectName;
    private String regDtim;
    private String pkgId;
    private String snstId;
    private String inputDate;

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

    public String getPkgId () {
        return pkgId;
    }

    public void setPkgId (String pkgId) {
        this.pkgId = pkgId;
    }

    public String getSnstId () {
        return snstId;
    }

    public void setSnstId (String snstId) {
        this.snstId = snstId;
    }

    public String getInputDate () {
        return inputDate;
    }

    public void setInputDate (String inputDate) {
        this.inputDate = inputDate;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("KeyInfo [");
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
        if (pkgId != null) {
            builder.append("pkgId=");
            builder.append(pkgId);
            builder.append(", ");
        }
        if (snstId != null) {
            builder.append("snstId=");
            builder.append(snstId);
            builder.append(", ");
        }
        if (inputDate != null) {
            builder.append("inputDate=");
            builder.append(inputDate);
        }
        builder.append("]");
        return builder.toString();
    }

}
