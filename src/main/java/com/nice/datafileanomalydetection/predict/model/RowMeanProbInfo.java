package com.nice.datafileanomalydetection.predict.model;

import org.springframework.stereotype.Component;

@Component
public class RowMeanProbInfo extends KeyInfo {

    private double rowMeanProbability;

    public double getRowMeanProbability () {
        return rowMeanProbability;
    }

    public void setRowMeanProbability (double rowMeanProbability) {
        this.rowMeanProbability = rowMeanProbability;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("RowMeanProbInfo [rowMeanProbability=");
        builder.append(rowMeanProbability);
        builder.append("]");
        return builder.toString();
    }

}