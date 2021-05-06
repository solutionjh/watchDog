package com.nice.datafileanomalydetection.predict.model;

public class TrainModelInfo {

    private String csvFilePath;
    private String header;
    private String projectName;
    private String requiredColumn;
    private String keyColumn;

    public String getCsvFilePath () {
        return csvFilePath;
    }

    public void setCsvFilePath (String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public String getHeader () {
        return header;
    }

    public void setHeader (String header) {
        this.header = header;
    }

    public String getProjectName () {
        return projectName;
    }

    public void setProjectName (String projectName) {
        this.projectName = projectName;
    }

    public String getRequiredColumn () {
        return requiredColumn;
    }

    public void setRequiredColumn (String requiredColumn) {
        this.requiredColumn = requiredColumn;
    }

    public String getKeyColumn () {
        return keyColumn;
    }

    public void setKeyColumn (String keyColumn) {
        this.keyColumn = keyColumn;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("TrainModelInfo [");
        if (csvFilePath != null) {
            builder.append("csvFilePath=");
            builder.append(csvFilePath);
            builder.append(", ");
        }
        if (header != null) {
            builder.append("header=");
            builder.append(header);
            builder.append(", ");
        }
        if (projectName != null) {
            builder.append("projectName=");
            builder.append(projectName);
            builder.append(", ");
        }
        if (requiredColumn != null) {
            builder.append("requiredColumn=");
            builder.append(requiredColumn);
            builder.append(", ");
        }
        if (keyColumn != null) {
            builder.append("keyColumn=");
            builder.append(keyColumn);
        }
        builder.append("]");
        return builder.toString();
    }

}
