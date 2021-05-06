package com.nice.datafileanomalydetection.predict.model;

import org.springframework.stereotype.Component;

@Component("predictInputInfo")
public class PredictInputInfo {

    private String projectName;
    private String inputDataFile;
    private String inputLayoutFile;
    private String inputFileEncoding;
    private String columnCalculateYN = "Y";

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

    public String getInputFileEncoding () {
        return inputFileEncoding;
    }

    public void setInputFileEncoding (String inputFileEncoding) {
        this.inputFileEncoding = inputFileEncoding;
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
        builder.append("PredictInputInfo [");
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
        if (inputFileEncoding != null) {
            builder.append("inputFileEncoding=");
            builder.append(inputFileEncoding);
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
