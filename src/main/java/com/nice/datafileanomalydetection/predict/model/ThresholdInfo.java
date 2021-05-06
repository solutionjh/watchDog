package com.nice.datafileanomalydetection.predict.model;

import org.springframework.stereotype.Component;

@Component("thresholdInfo")
public class ThresholdInfo {

    private String projectName;
    private double psiThreshold;
    private double changeRateThreshold;
    private double chiSqThreshold;

    public String getProjectName () {
        return projectName;
    }

    public void setProjectName (String projectName) {
        this.projectName = projectName;
    }

    public double getPsiThreshold () {
        return psiThreshold;
    }

    public void setPsiThreshold (double psiThreshold) {
        this.psiThreshold = psiThreshold;
    }

    public double getChangeRateThreshold () {
        return changeRateThreshold;
    }

    public void setChangeRateThreshold (double changeRateThreshold) {
        this.changeRateThreshold = changeRateThreshold;
    }

    public double getChiSqThreshold () {
        return chiSqThreshold;
    }

    public void setChiSqThreshold (double chiSqThreshold) {
        this.chiSqThreshold = chiSqThreshold;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("ThresholdInfo [");
        if (projectName != null) {
            builder.append("projectName=");
            builder.append(projectName);
            builder.append(", ");
        }
        builder.append("psiThreshold=");
        builder.append(psiThreshold);
        builder.append(", changeRateThreshold=");
        builder.append(changeRateThreshold);
        builder.append(", chiSqThreshold=");
        builder.append(chiSqThreshold);
        builder.append("]");
        return builder.toString();
    }

}
