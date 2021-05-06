package com.nice.datafileanomalydetection.online.model;

import org.springframework.stereotype.Component;

@Component
public class OnlineRowDistResult extends OnlineKey {

    private double prob0To5;
    private double prob5To10;
    private double prob10To15;
    private double prob15To20;
    private double prob20To25;
    private double prob25To30;
    private double prob30To35;
    private double prob35To40;
    private double prob40To45;
    private double prob45To50;
    private double prob50To55;
    private double prob55To60;
    private double prob60To65;
    private double prob65To70;
    private double prob70To75;
    private double prob75To80;
    private double prob80To85;
    private double prob85To90;
    private double prob90To95;
    private double prob95To100;

    public double getProb0To5 () {
        return prob0To5;
    }

    public void setProb0To5 (double prob0To5) {
        this.prob0To5 = prob0To5;
    }

    public double getProb5To10 () {
        return prob5To10;
    }

    public void setProb5To10 (double prob5To10) {
        this.prob5To10 = prob5To10;
    }

    public double getProb10To15 () {
        return prob10To15;
    }

    public void setProb10To15 (double prob10To15) {
        this.prob10To15 = prob10To15;
    }

    public double getProb15To20 () {
        return prob15To20;
    }

    public void setProb15To20 (double prob15To20) {
        this.prob15To20 = prob15To20;
    }

    public double getProb20To25 () {
        return prob20To25;
    }

    public void setProb20To25 (double prob20To25) {
        this.prob20To25 = prob20To25;
    }

    public double getProb25To30 () {
        return prob25To30;
    }

    public void setProb25To30 (double prob25To30) {
        this.prob25To30 = prob25To30;
    }

    public double getProb30To35 () {
        return prob30To35;
    }

    public void setProb30To35 (double prob30To35) {
        this.prob30To35 = prob30To35;
    }

    public double getProb35To40 () {
        return prob35To40;
    }

    public void setProb35To40 (double prob35To40) {
        this.prob35To40 = prob35To40;
    }

    public double getProb40To45 () {
        return prob40To45;
    }

    public void setProb40To45 (double prob40To45) {
        this.prob40To45 = prob40To45;
    }

    public double getProb45To50 () {
        return prob45To50;
    }

    public void setProb45To50 (double prob45To50) {
        this.prob45To50 = prob45To50;
    }

    public double getProb50To55 () {
        return prob50To55;
    }

    public void setProb50To55 (double prob50To55) {
        this.prob50To55 = prob50To55;
    }

    public double getProb55To60 () {
        return prob55To60;
    }

    public void setProb55To60 (double prob55To60) {
        this.prob55To60 = prob55To60;
    }

    public double getProb60To65 () {
        return prob60To65;
    }

    public void setProb60To65 (double prob60To65) {
        this.prob60To65 = prob60To65;
    }

    public double getProb65To70 () {
        return prob65To70;
    }

    public void setProb65To70 (double prob65To70) {
        this.prob65To70 = prob65To70;
    }

    public double getProb70To75 () {
        return prob70To75;
    }

    public void setProb70To75 (double prob70To75) {
        this.prob70To75 = prob70To75;
    }

    public double getProb75To80 () {
        return prob75To80;
    }

    public void setProb75To80 (double prob75To80) {
        this.prob75To80 = prob75To80;
    }

    public double getProb80To85 () {
        return prob80To85;
    }

    public void setProb80To85 (double prob80To85) {
        this.prob80To85 = prob80To85;
    }

    public double getProb85To90 () {
        return prob85To90;
    }

    public void setProb85To90 (double prob85To90) {
        this.prob85To90 = prob85To90;
    }

    public double getProb90To95 () {
        return prob90To95;
    }

    public void setProb90To95 (double prob90To95) {
        this.prob90To95 = prob90To95;
    }

    public double getProb95To100 () {
        return prob95To100;
    }

    public void setProb95To100 (double prob95To100) {
        this.prob95To100 = prob95To100;
    }

    @Override
    public String toString () {
        return "OnlineRowDistResult [prob0To5=" + prob0To5 + ", prob5To10=" + prob5To10 + ", prob10To15=" + prob10To15
                + ", prob15To20=" + prob15To20 + ", prob20To25=" + prob20To25 + ", prob25To30=" + prob25To30
                + ", prob30To35=" + prob30To35 + ", prob35To40=" + prob35To40 + ", prob40To45=" + prob40To45
                + ", prob45To50=" + prob45To50 + ", prob50To55=" + prob50To55 + ", prob55To60=" + prob55To60
                + ", prob60To65=" + prob60To65 + ", prob65To70=" + prob65To70 + ", prob70To75=" + prob70To75
                + ", prob75To80=" + prob75To80 + ", prob80To85=" + prob80To85 + ", prob85To90=" + prob85To90
                + ", prob90To95=" + prob90To95 + ", prob95To100=" + prob95To100 + "]";
    }

    public String getRowDistResult () {
        return getProjectName() + "," + getPkgId() + "," + getSnstId() + "," + getInputDate() + "," + getRegDtim()
                + "," + prob0To5 + "," + prob5To10 + "," + prob10To15
                + "," + prob15To20 + "," + prob20To25 + "," + prob25To30
                + "," + prob30To35 + "," + prob35To40 + "," + prob40To45
                + "," + prob45To50 + "," + prob50To55 + "," + prob55To60
                + "," + prob60To65 + "," + prob65To70 + "," + prob70To75
                + "," + prob75To80 + "," + prob80To85 + "," + prob85To90
                + "," + prob90To95 + "," + prob95To100;
    }

    public String getHeader () {
        return String.join(",", new String[]{"PROJECTNAME", "PKGID", "SNSTID", "INPUTDATE", "REGDTIM", "PROB0TO5", "PROB5TO10", "PROB10TO15", "PROB15TO20", "PROB20TO25", "PROB25TO30", "PROB30TO35", "PROB35TO40", "PROB40TO45", "PROB45TO50", "PROB50TO55", "PROB55TO60", "PROB60TO65", "PROB65TO70", "PROB70TO75", "PROB75TO80", "PROB80TO85", "PROB85TO90", "PROB90TO95", "PROB95TO100"});
    }
}
