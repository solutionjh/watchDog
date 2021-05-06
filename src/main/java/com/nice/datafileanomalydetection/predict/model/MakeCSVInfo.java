package com.nice.datafileanomalydetection.predict.model;

import org.springframework.stereotype.Component;

@Component("makeCSVInfo")
public class MakeCSVInfo {

    private long inputDataCount;
    private String inputItemList;

    public long getInputDataCount () {
        return inputDataCount;
    }

    public void setInputDataCount (long inputDataCount) {
        this.inputDataCount = inputDataCount;
    }

    public String getInputItemList () {
        return inputItemList;
    }

    public void setInputItemList (String inputItemList) {
        this.inputItemList = inputItemList;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("MakeCSVInfo [inputDataCount=");
        builder.append(inputDataCount);
        builder.append(", ");
        if (inputItemList != null) {
            builder.append("inputItemList=");
            builder.append(inputItemList);
        }
        builder.append("]");
        return builder.toString();
    }

}
