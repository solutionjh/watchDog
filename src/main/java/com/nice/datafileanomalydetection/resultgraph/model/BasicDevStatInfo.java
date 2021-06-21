package com.nice.datafileanomalydetection.resultgraph.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component("basicDevStatInfo")
public class BasicDevStatInfo implements Comparable<BasicDevStatInfo> {

    private String projectName;
    private String regDtim;
    private String fileName;
    private String fieldName;
    private String average;
    private String averageDev;
    private String standardDeviation;
    private String standardDeviationDev;
    private String min;
    private String minDev;
    private String max;
    private String maxDev;
    private String median;
    private String medianDev;
    private String firstQuantile;
    private String firstQuantileDev;
    private String thirdQuantile;
    private String thirdQuantileDev;

    public BasicDevStatInfo() {
    }

    public BasicDevStatInfo(String projectName, String regDtim, String fileName, String fieldName, String average, String averageDev, String standardDeviation, String standardDeviationDev, String min, String minDev, String max, String maxDev, String median, String medianDev, String firstQuantile, String firstQuantileDev, String thirdQuantile, String thirdQuantileDev) {
        this.projectName = projectName;
        this.regDtim = regDtim;
        this.fileName = fileName;
        this.fieldName = fieldName;
        this.average = average;
        this.averageDev = averageDev;
        this.standardDeviation = standardDeviation;
        this.standardDeviationDev = standardDeviationDev;
        this.min = min;
        this.minDev = minDev;
        this.max = max;
        this.maxDev = maxDev;
        this.median = median;
        this.medianDev = medianDev;
        this.firstQuantile = firstQuantile;
        this.firstQuantileDev = firstQuantileDev;
        this.thirdQuantile = thirdQuantile;
        this.thirdQuantileDev = thirdQuantileDev;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRegDtim() {
        return regDtim;
    }

    public void setRegDtim(String regDtim) {
        this.regDtim = regDtim;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getAverageDev() {
        return averageDev;
    }

    public void setAverageDev(String averageDev) {
        this.averageDev = averageDev;
    }

    public String getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(String standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public String getStandardDeviationDev() {
        return standardDeviationDev;
    }

    public void setStandardDeviationDev(String standardDeviationDev) {
        this.standardDeviationDev = standardDeviationDev;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMinDev() {
        return minDev;
    }

    public void setMinDev(String minDev) {
        this.minDev = minDev;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMaxDev() {
        return maxDev;
    }

    public void setMaxDev(String maxDev) {
        this.maxDev = maxDev;
    }

    public String getMedian() {
        return median;
    }

    public void setMedian(String median) {
        this.median = median;
    }

    public String getMedianDev() {
        return medianDev;
    }

    public void setMedianDev(String medianDev) {
        this.medianDev = medianDev;
    }

    public String getFirstQuantile() {
        return firstQuantile;
    }

    public void setFirstQuantile(String firstQuantile) {
        this.firstQuantile = firstQuantile;
    }

    public String getFirstQuantileDev() {
        return firstQuantileDev;
    }

    public void setFirstQuantileDev(String firstQuantileDev) {
        this.firstQuantileDev = firstQuantileDev;
    }

    public String getThirdQuantile() {
        return thirdQuantile;
    }

    public void setThirdQuantile(String thirdQuantile) {
        this.thirdQuantile = thirdQuantile;
    }

    public String getThirdQuantileDev() {
        return thirdQuantileDev;
    }

    public void setThirdQuantileDev(String thirdQuantileDev) {
        this.thirdQuantileDev = thirdQuantileDev;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("projectName", projectName)
                .append("regDtim", regDtim)
                .append("fileName", fileName)
                .append("fieldName", fieldName)
                .append("average", average)
                .append("averageDev", averageDev)
                .append("standardDeviation", standardDeviation)
                .append("standardDeviationDev", standardDeviationDev)
                .append("min", min)
                .append("minDev", minDev)
                .append("max", max)
                .append("maxDev", maxDev)
                .append("median", median)
                .append("medianDev", medianDev)
                .append("firstQuantile", firstQuantile)
                .append("firstQuantileDev", firstQuantileDev)
                .append("thirdQuantile", thirdQuantile)
                .append("thirdQuantileDev", thirdQuantileDev)
                .toString();
    }

    @Override
    public int compareTo(@NotNull BasicDevStatInfo o) {
        return -this.regDtim.compareTo(o.getRegDtim());
    }
}