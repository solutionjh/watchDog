package com.nice.datafileanomalydetection.predict.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("predictInfo")
public class PredictInfo {

    private double rowMse;

    private List rowSquardErrorInfo;

    public double getRowMse () {
        return rowMse;
    }

    public void setRowMse (double rowMse) {
        this.rowMse = rowMse;
    }

    public List getRowSquardErrorInfo () {
        return rowSquardErrorInfo;
    }

    public void setRowSquardErrorInfo (List rowSquardErrorInfo) {
        this.rowSquardErrorInfo = rowSquardErrorInfo;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("PredictInfo [rowMse=");
        builder.append(rowMse);
        builder.append(", ");
        if (rowSquardErrorInfo != null) {
            builder.append("rowSquardErrorInfo=");
            builder.append(rowSquardErrorInfo);
        }
        builder.append("]");
        return builder.toString();
    }

}