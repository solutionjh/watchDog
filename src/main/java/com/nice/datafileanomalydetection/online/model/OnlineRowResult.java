package com.nice.datafileanomalydetection.online.model;

import org.springframework.stereotype.Component;

@Component
public class OnlineRowResult extends OnlineKey {

    private double meanProb;
    private double minProb;
    private double pcntl25Prob;
    private double medianProb;
    private double pcntl75Prob;
    private double pcntl90Prob;
    private double pcntl95Prob;
    private double pcntl99Prob;
    private double maxProb;

    public double getMeanProb () {
        return meanProb;
    }

    public void setMeanProb (double meanProb) {
        this.meanProb = meanProb;
    }

    public double getMinProb () {
        return minProb;
    }

    public void setMinProb (double minProb) {
        this.minProb = minProb;
    }

    public double getPcntl25Prob () {
        return pcntl25Prob;
    }

    public void setPcntl25Prob (double pcntl25Prob) {
        this.pcntl25Prob = pcntl25Prob;
    }

    public double getMedianProb () {
        return medianProb;
    }

    public void setMedianProb (double medianProb) {
        this.medianProb = medianProb;
    }

    public double getPcntl75Prob () {
        return pcntl75Prob;
    }

    public void setPcntl75Prob (double pcntl75Prob) {
        this.pcntl75Prob = pcntl75Prob;
    }

    public double getPcntl90Prob () {
        return pcntl90Prob;
    }

    public void setPcntl90Prob (double pcntl90Prob) {
        this.pcntl90Prob = pcntl90Prob;
    }

    public double getPcntl95Prob () {
        return pcntl95Prob;
    }

    public void setPcntl95Prob (double pcntl95Prob) {
        this.pcntl95Prob = pcntl95Prob;
    }

    public double getPcntl99Prob () {
        return pcntl99Prob;
    }

    public void setPcntl99Prob (double pcntl99Prob) {
        this.pcntl99Prob = pcntl99Prob;
    }

    public double getMaxProb () {
        return maxProb;
    }

    public void setMaxProb (double maxProb) {
        this.maxProb = maxProb;
    }

    @Override
    public String toString () {
        return "OnlineRowResult [projectName=" + getProjectName() + ", regDtim=" + getRegDtim() + ", meanProb=" + meanProb + ", minProb=" + minProb + ", pcntl25Prob=" + pcntl25Prob
                + ", medianProb=" + medianProb + ", pcntl75Prob=" + pcntl75Prob + ", pcntl90Prob=" + pcntl90Prob
                + ", pcntl95Prob=" + pcntl95Prob + ", pcntl99Prob=" + pcntl99Prob + ", maxProb=" + maxProb + "]";
    }

    public String getRowResult () {
        return getProjectName() + "," + getPkgId() + "," + getSnstId() + "," + getInputDate() + "," + getRegDtim() + "," + meanProb + "," + minProb + "," + pcntl25Prob
                + "," + medianProb + "," + pcntl75Prob + "," + pcntl90Prob
                + "," + pcntl95Prob + "," + pcntl99Prob + "," + maxProb;
    }

    public String getHeader () {
        return String.join(",", new String[]{"PROJECTNAME", "PKGID", "SNSTID", "INPUTDATE", "REGDTIM", "MEANPROB", "MINPROB", "PCNTL25PROB", "MEDIANPROB", "PCNTL75PROB", "PCNTL90PROB", "PCNTL95PROB", "PCNTL99PROB", "MAXPROB"});
    }
}
