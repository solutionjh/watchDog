package com.nice.datafileanomalydetection.online.model;

import org.springframework.stereotype.Component;

@Component
public class OnlineKey {

    private String projectName;
    private String regDtim;
    private String inputDate;
    private String pkgId;
    private String snstId;
    private String aplyDtim;

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

    public String getAplyDtim () {
        return aplyDtim;
    }

    public void setAplyDtim (String aplyDtim) {
        this.aplyDtim = aplyDtim;
    }

    public String getInputDate () {
        return inputDate;
    }

    public void setInputDate (String inputDate) {
        this.inputDate = inputDate;
    }

    @Override
    public String toString () {
        return "OnlineKey [projectName=" + projectName + ", regDtim=" + regDtim + ", inputDate=" + inputDate
                + ", pkgId=" + pkgId + ", snstId=" + snstId + ", aplyDtim=" + aplyDtim + "]";
    }


}
