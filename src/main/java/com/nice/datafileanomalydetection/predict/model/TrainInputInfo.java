package com.nice.datafileanomalydetection.predict.model;

public class TrainInputInfo {

    private String makeModelYN = "Y";
    private String projectName;
    private String inputDataFile;
    private String inputLayoutFile;
    private String columnCalculateYN = "Y";

    public String getMakeModelYN () {
        return makeModelYN;
    }

    public void setMakeModelYN (String makeModelYN) {
        this.makeModelYN = makeModelYN;
    }

    public String getProjectName () {
        return projectName;
    }

    public void setProjectName (String projectName) {
        this.projectName = projectName;
    }

    public String getInputDataFile () {
        return inputDataFile;
    }

    public void setInputDataFile (String inputDataFile) {
        this.inputDataFile = inputDataFile;
    }

    public String getInputLayoutFile () {
        return inputLayoutFile;
    }

    public void setInputLayoutFile (String inputLayoutFile) {
        this.inputLayoutFile = inputLayoutFile;
    }

    public String getColumnCalculateYN () {
        return columnCalculateYN;
    }

    public void setColumnCalculateYN (String columnCalculateYN) {
        this.columnCalculateYN = columnCalculateYN;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("TrainInputInfo [");
        if (makeModelYN != null) {
            builder.append("makeModelYN=");
            builder.append(makeModelYN);
            builder.append(", ");
        }
        if (projectName != null) {
            builder.append("projectName=");
            builder.append(projectName);
            builder.append(", ");
        }
        if (inputDataFile != null) {
            builder.append("inputDataFile=");
            builder.append(inputDataFile);
            builder.append(", ");
        }
        if (inputLayoutFile != null) {
            builder.append("inputLayoutFile=");
            builder.append(inputLayoutFile);
            builder.append(", ");
        }
        if (columnCalculateYN != null) {
            builder.append("columnCalculateYN=");
            builder.append(columnCalculateYN);
        }
        builder.append("]");
        return builder.toString();
    }

}
