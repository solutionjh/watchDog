package com.nice.datafileanomalydetection.resultitemdist.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component("resultItemDist")
public class ResultItemDist implements Comparable<ResultItemDist> {

    private String fieldName;
    private String itemValue;
    private String devProbability;
    private String nowProbability;
    private String diff;
    private String isAnomaly;
    private String alertMark;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue (String itemValue) {
        this.itemValue = itemValue;
    }

    public String getDevProbability () {
        return devProbability;
    }

    public void setDevProbability (String devProbability) {
        this.devProbability = devProbability;
    }

    public String getNowProbability () {
        return nowProbability;
    }

    public void setNowProbability (String nowProbability) {
        this.nowProbability = nowProbability;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getIsAnomaly() {
        return isAnomaly;
    }

    public void setIsAnomaly(String isAnomaly) {
        this.isAnomaly = isAnomaly;
    }

    public String getAlertMark() {
        return alertMark;
    }

    public void setAlertMark(String alertMark) {
        this.alertMark = alertMark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fieldName", fieldName)
                .append("itemValue", itemValue)
                .append("devProbability", devProbability)
                .append("nowProbability", nowProbability)
                .append("diff", diff)
                .append("isAnomaly", isAnomaly)
                .append("alertMark", alertMark)
                .toString();
    }

    @Override
    public int compareTo(@NotNull ResultItemDist o) {
        int result = -new BigDecimal(this.diff).compareTo(new BigDecimal(o.getDiff()));
        if (result == 0) {
            result = this.fieldName.compareTo(o.getFieldName());
        }
        return result;
    }
}