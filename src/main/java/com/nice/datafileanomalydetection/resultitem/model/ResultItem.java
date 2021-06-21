package com.nice.datafileanomalydetection.resultitem.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component("resultItem")
public class ResultItem implements Comparable<ResultItem> {

    private String fieldName;
    private String devMse;
    private String nowMse;
    private String jobGb;
    private String changeRate;
    private String isAnomaly;
    private String alertMark;

    public ResultItem() {
        this.isAnomaly = "0";
        this.alertMark = "0";
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDevMse() {
        return devMse;
    }

    public void setDevMse (String devMse) {
        this.devMse = devMse;
    }

    public String getNowMse () {
        return nowMse;
    }

    public void setNowMse (String nowMse) {
        this.nowMse = nowMse;
    }

    public String getJobGb () {
        return jobGb;
    }

    public void setJobGb (String jobGb) {
        this.jobGb = jobGb;
    }

    public String getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(String changeRate) {
        this.changeRate = changeRate;
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
                .append("devMse", devMse)
                .append("nowMse", nowMse)
                .append("jobGb", jobGb)
                .append("changeRate", changeRate)
                .append("isAnomaly", isAnomaly)
                .append("alertMark", alertMark)
                .toString();
    }

    /**
     * 정렬순서: changeRate(내림차순) -> fieldName(오름차순)
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(@NotNull ResultItem o) {
        int result = -new BigDecimal(this.changeRate).compareTo(new BigDecimal(o.getChangeRate()));
        if (result == 0) {
            result = this.fieldName.compareTo(o.getFieldName());
        }
        return result;
    }
}