package com.nice.datafileanomalydetection.predict.model;

public class RowSquaredErrorInfo {

    private String projectName;
    private String regDtim;
    private String fieldName;
    private double squaredError;
    private double count;

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

    public double getSquaredError () {
        return squaredError;
    }

    public void setSquaredError (double squaredError) {
        this.squaredError = squaredError;
    }

    public double getCount () {
        return count;
    }

    public void setCount (double count) {
        this.count = count;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("RowSquaredErrorInfo [");
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
        builder.append("squaredError=");
        builder.append(squaredError);
        builder.append(", count=");
        builder.append(count);
        builder.append("]");
        return builder.toString();
    }

}
