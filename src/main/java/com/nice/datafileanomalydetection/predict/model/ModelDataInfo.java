package com.nice.datafileanomalydetection.predict.model;

public class ModelDataInfo {

    private String projectName;
    private String regDtim;
    private String fieldName;
    private String fieldType;
    private String modelUse;
    private double mean;
    private double stddev;
    private double minimum;
    private double maximum;
    private double median;
    private double lq;
    private double uq;
    private double iqr;
    private long naCnt;
    private long nzCnt;
    private String mode;

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

    public String getFieldType () {
        return fieldType;
    }

    public void setFieldType (String fieldType) {
        this.fieldType = fieldType;
    }

    public String getModelUse () {
        return modelUse;
    }

    public void setModelUse (String modelUse) {
        this.modelUse = modelUse;
    }

    public double getMean () {
        return mean;
    }

    public void setMean (double mean) {
        this.mean = mean;
    }

    public double getStddev () {
        return stddev;
    }

    public void setStddev (double stddev) {
        this.stddev = stddev;
    }

    public double getMinimum () {
        return minimum;
    }

    public void setMinimum (double minimum) {
        this.minimum = minimum;
    }

    public double getMaximum () {
        return maximum;
    }

    public void setMaximum (double maximum) {
        this.maximum = maximum;
    }

    public double getMedian () {
        return median;
    }

    public void setMedian (double median) {
        this.median = median;
    }

    public double getLq () {
        return lq;
    }

    public void setLq (double lq) {
        this.lq = lq;
    }

    public double getUq () {
        return uq;
    }

    public void setUq (double uq) {
        this.uq = uq;
    }

    public double getIqr () {
        return iqr;
    }

    public void setIqr (double iqr) {
        this.iqr = iqr;
    }

    public long getNaCnt () {
        return naCnt;
    }

    public void setNaCnt (long naCnt) {
        this.naCnt = naCnt;
    }

    public long getNzCnt () {
        return nzCnt;
    }

    public void setNzCnt (long nzCnt) {
        this.nzCnt = nzCnt;
    }

    public String getMode () {
        return mode;
    }

    public void setMode (String mode) {
        this.mode = mode;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("ModelDataInfo [");
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
        if (fieldType != null) {
            builder.append("fieldType=");
            builder.append(fieldType);
            builder.append(", ");
        }
        if (modelUse != null) {
            builder.append("modelUse=");
            builder.append(modelUse);
            builder.append(", ");
        }
        builder.append("mean=");
        builder.append(mean);
        builder.append(", stddev=");
        builder.append(stddev);
        builder.append(", minimum=");
        builder.append(minimum);
        builder.append(", maximum=");
        builder.append(maximum);
        builder.append(", median=");
        builder.append(median);
        builder.append(", lq=");
        builder.append(lq);
        builder.append(", uq=");
        builder.append(uq);
        builder.append(", iqr=");
        builder.append(iqr);
        builder.append(", naCnt=");
        builder.append(naCnt);
        builder.append(", nzCnt=");
        builder.append(nzCnt);
        builder.append(", ");
        if (mode != null) {
            builder.append("mode=");
            builder.append(mode);
        }
        builder.append("]");
        return builder.toString();
    }

}
