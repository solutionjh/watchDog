package com.nice.datafileanomalydetection.result.model;

public class LearningDataInfo implements Comparable<LearningDataInfo> {
    String projectName;
    String fieldName;
    String modelUse;
    String modelUseRsn;
    double mean;
    double stddev;
    double min;
    double lq;
    double median;
    double uq;
    double max;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getModelUse() {
        return modelUse;
    }

    public void setModelUse(String modelUse) {
        this.modelUse = modelUse;
    }

    public String getModelUseRsn() {
        return modelUseRsn;
    }

    public void setModelUseRsn(String modelUseRsn) {
        this.modelUseRsn = modelUseRsn;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getStddev() {
        return stddev;
    }

    public void setStddev(double stddev) {
        this.stddev = stddev;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getLq() {
        return lq;
    }

    public void setLq(double lq) {
        this.lq = lq;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public double getUq() {
        return uq;
    }

    public void setUq(double uq) {
        this.uq = uq;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LearningDataInfo{");
        sb.append("projectName='").append(projectName).append('\'');
        sb.append(", fieldName='").append(fieldName).append('\'');
        sb.append(", modelUse='").append(modelUse).append('\'');
        sb.append(", modelUseRsn='").append(modelUseRsn).append('\'');
        sb.append(", mean=").append(mean);
        sb.append(", stddev=").append(stddev);
        sb.append(", min=").append(min);
        sb.append(", lq=").append(lq);
        sb.append(", median=").append(median);
        sb.append(", uq=").append(uq);
        sb.append(", max=").append(max);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(LearningDataInfo learningDataInfo) {
        int result = -this.modelUse.compareTo(learningDataInfo.getModelUse());

        if (result == 0) {
            result = this.fieldName.compareTo(learningDataInfo.getFieldName());
        }

        return result;
    }
}
