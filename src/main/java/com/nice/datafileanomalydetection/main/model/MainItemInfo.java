package com.nice.datafileanomalydetection.main.model;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component("mainItemInfo")
public class MainItemInfo implements Comparable<MainItemInfo> {

    private String projectName;
    private String baseDtim;
    private String fieldName;
    private String changeRate;
    private String detectedCnt;

    public MainItemInfo() {
    }

    public MainItemInfo(String projectName, String baseDtim, String fieldName, String changeRate, String detectedCnt) {
        this.projectName = projectName;
        this.baseDtim = baseDtim;
        this.fieldName = fieldName;
        this.changeRate = changeRate;
        this.detectedCnt = detectedCnt;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBaseDtim() {
        return baseDtim;
    }

    public void setBaseDtim(String baseDtim) {
        this.baseDtim = baseDtim;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getChangeRate() {
        return changeRate;
    }

    public void setChangeRate (String changeRate) {
        this.changeRate = changeRate;
    }

    public String getDetectedCnt() {
        return detectedCnt;
    }

    public void setDetectedCnt(String detectedCnt) {
        this.detectedCnt = detectedCnt;
    }

    /**
     * 정렬순서: baseDtim(내림차순) -> projectName(오름차순) -> changeRate(내림차순)
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(@NotNull MainItemInfo o) {
        if (this.baseDtim.compareTo(o.getBaseDtim()) == 0) {
            if (this.projectName.compareTo(o.getProjectName()) == 0) {
                return -this.changeRate.compareTo(o.getChangeRate());
            } else {
                return this.projectName.compareTo(o.getProjectName());
            }
        } else {
            return -this.baseDtim.compareTo((o.getBaseDtim()));
        }
    }
}