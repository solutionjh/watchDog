package com.nice.datafileanomalydetection.predict.model;

import org.springframework.stereotype.Component;

@Component("makeCSVInputInfo")
public class MakeCSVInputInfo {

    private String inputDataFile;
    private String inputLayoutFile;
    private String outputDataFile;

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

    public String getOutputDataFile () {
        return outputDataFile;
    }

    public void setOutputDataFile (String outputDataFile) {
        this.outputDataFile = outputDataFile;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("MakeCSVInputInfo [");
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
        if (outputDataFile != null) {
            builder.append("outputDataFile=");
            builder.append(outputDataFile);
        }
        builder.append("]");
        return builder.toString();
    }

}
