package com.nice.datafileanomalydetection.resultgraph.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component("basicStatInfo")
public class BasicStatInfo implements Comparable<BasicStatInfo> {

    private String projectName;
    private String regDtim;
    private String fileName;
    private String fieldName;
    private String average;
    private String standardDeviation;
    private String min;
    private String max;
    private String median;
    private String firstQuantile;
    private String thirdQuantile;

    public BasicStatInfo() {
    }

    public BasicStatInfo(String projectName, String regDtim, String fileName, String fieldName, String average, String standardDeviation, String min, String max, String median, String firstQuantile, String thirdQuantile) {
        this.projectName = projectName;
        this.regDtim = regDtim;
        this.fileName = fileName;
        this.fieldName = fieldName;
        this.average = average;
        this.standardDeviation = standardDeviation;
        this.min = min;
        this.max = max;
        this.median = median;
        this.firstQuantile = firstQuantile;
        this.thirdQuantile = thirdQuantile;
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

    public String getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(String standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMedian() {
        return median;
    }

    public void setMedian(String median) {
        this.median = median;
    }

    public String getFirstQuantile() {
        return firstQuantile;
    }

    public void setFirstQuantile(String firstQuantile) {
        this.firstQuantile = firstQuantile;
    }

    public String getThirdQuantile() {
        return thirdQuantile;
    }

    public void setThirdQuantile(String thirdQuantile) {
        this.thirdQuantile = thirdQuantile;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("projectName", projectName)
                .append("regDtim", regDtim)
                .append("fileName", fileName)
                .append("fieldName", fieldName)
                .append("average", average)
                .append("standardDeviation", standardDeviation)
                .append("min", min)
                .append("max", max)
                .append("median", median)
                .append("firstQuantile", firstQuantile)
                .append("thirdQuantile", thirdQuantile)
                .toString();
    }

    @Override
    public int compareTo(@NotNull BasicStatInfo o) {
        return -this.regDtim.compareTo(o.getRegDtim());
    }
}