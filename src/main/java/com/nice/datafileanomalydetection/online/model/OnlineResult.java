package com.nice.datafileanomalydetection.online.model;

import org.springframework.stereotype.Component;

@Component
public class OnlineResult extends OnlineKey {

    private String endDtim;
    private int modelItemCnt;
    private String inputFileName;
    private long dataCnt;
    private double anomalyRatio;
    private int anomalyItemCnt;
    private int anomalyResult;
    private double anomalyMeanProb;
    private String comment;


    public String getEndDtim () {
        return endDtim;
    }

    public void setEndDtim (String endDtim) {
        this.endDtim = endDtim;
    }

    public String getInputFileName () {
        return inputFileName;
    }

    public void setInputFileName (String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public long getDataCnt () {
        return dataCnt;
    }

    public void setDataCnt (long dataCnt) {
        this.dataCnt = dataCnt;
    }

    public double getAnomalyRatio () {
        return anomalyRatio;
    }

    public void setAnomalyRatio (double anomalyRatio) {
        this.anomalyRatio = anomalyRatio;
    }

    public int getAnomalyItemCnt () {
        return anomalyItemCnt;
    }

    public void setAnomalyItemCnt (int anomalyItemCnt) {
        this.anomalyItemCnt = anomalyItemCnt;
    }

    public int getAnomalyResult () {
        return anomalyResult;
    }

    public void setAnomalyResult (int anomalyResult) {
        this.anomalyResult = anomalyResult;
    }

    public String getComment () {
        return comment;
    }

    public void setComment (String comment) {
        this.comment = comment;
    }

    public int getModelItemCnt () {
        return modelItemCnt;
    }

    public void setModelItemCnt (int modelItemCnt) {
        this.modelItemCnt = modelItemCnt;
    }

    public double getAnomalyMeanProb () {
        return anomalyMeanProb;
    }

    public void setAnomalyMeanProb (double anomalyMeanProb) {
        this.anomalyMeanProb = anomalyMeanProb;
    }

    @Override
    public String toString () {
        return "OnlineResult [projectName=" + getProjectName() + ", pkgId=" + getPkgId() + ", snstId=" + getSnstId() + ", inputDate=" + getInputDate() + ", regDtim=" + getRegDtim() + ", endDtim=" + endDtim + ", modelItemCnt=" + getModelItemCnt() + ", dataCnt=" + dataCnt + ", anomalyMeanProb=" + getAnomalyMeanProb() + ", anomalyRatio=" + anomalyRatio
                + ", anomalyItemCnt=" + anomalyItemCnt + ", anomalyResult=" + anomalyResult + ", comment=" + comment + "]";
    }

    public String getResult () {
        return getProjectName() + "," + getPkgId() + "," + getSnstId() + "," + getInputDate() + "," + modelItemCnt + "," + getRegDtim() + "," + endDtim + "," + dataCnt + "," + anomalyMeanProb + "," + anomalyRatio + "," + anomalyItemCnt + "," + anomalyResult;
    }

    public String getHeader () {
        return String.join(",", new String[]{"PROJECTNAME", "PKGID", "SNSTID", "INPUTDATE", "MODELITEMCNT", "REGDTIM", "ENDDTIM", "DATACNT", "ANOMALYMEANPROB", "ANOMALYRATIO", "ANOMALYITEMCNT", "ANOMALYRESULT"});
    }

}
