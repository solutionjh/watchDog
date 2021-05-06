package com.nice.datafileanomalydetection.predict.model;

public class RowMeanSquaredErrorInfo {

    private String projectName;
    private String regDtim;
    private double meanSquaredError;

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

    public double getMeanSquaredError () {
        return meanSquaredError;
    }

    public void setMeanSquaredError (double meanSquaredError) {
        this.meanSquaredError = meanSquaredError;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("RowMeanSquaredErrorInfo [");
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
        builder.append("meanSquaredError=");
        builder.append(meanSquaredError);
        builder.append("]");
        return builder.toString();
    }

}
