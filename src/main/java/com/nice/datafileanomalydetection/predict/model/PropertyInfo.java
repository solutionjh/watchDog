package com.nice.datafileanomalydetection.predict.model;

import java.util.Properties;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.stereotype.Component;

@Component("propertyInfo")
public class PropertyInfo {

    @ApiModelProperty(value = "데이터파일 종류", notes = "Y: 고정길이, N:가변길이", allowableValues = "Y, N")
    private String fixedLayoutYN;
    private String header;
    private String columnLength;
    private String delimiter;
    private String headerToSkip;
    private String footerToSkip;

    private String ratioGrade;
    private String threshold;

    private String keyColumn;
    private String requiredColumn;

    private String fileName;
    @ApiModelProperty(value = "파일형식", allowableValues = "fixed, csv, json")
    private String formatGb;

    public PropertyInfo() {

    }

    public PropertyInfo(Properties properties) {
        this.fixedLayoutYN = properties.getProperty("fixedlayoutyn");
        this.header = properties.getProperty("header");
        this.columnLength = properties.getProperty("columnlength");
        this.delimiter = properties.getProperty("delimeter");
        this.headerToSkip = properties.getProperty("headertoskip");
        this.footerToSkip = properties.getProperty("footertoskip");
        this.ratioGrade = properties.getProperty("ratiograde");
        this.threshold = properties.getProperty("threshold");
        this.keyColumn = properties.getProperty("keycolumn");
        this.requiredColumn = properties.getProperty("requiredcolumn");
        this.formatGb = properties.getProperty("formatgb", "csv");
    }

    public String getFixedLayoutYN() {
        return fixedLayoutYN;
    }

    public void setFixedLayoutYN(String fixedLayoutYN) {
        this.fixedLayoutYN = fixedLayoutYN;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(String columnLength) {
        this.columnLength = columnLength;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getHeaderToSkip() {
        return headerToSkip;
    }

    public void setHeaderToSkip(String headerToSkip) {
        this.headerToSkip = headerToSkip;
    }

    public String getFooterToSkip() {
        return footerToSkip;
    }

    public void setFooterToSkip(String footerToSkip) {
        this.footerToSkip = footerToSkip;
    }

    public String getRatioGrade() {
        return ratioGrade;
    }

    public void setRatioGrade(String ratioGrade) {
        this.ratioGrade = ratioGrade;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    public String getRequiredColumn() {
        return requiredColumn;
    }

    public void setRequiredColumn(String requiredColumn) {
        this.requiredColumn = requiredColumn;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFormatGb() {
        return formatGb;
    }

    public void setFormatGb(String formatGb) {
        this.formatGb = formatGb;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fixedLayoutYN", fixedLayoutYN)
                .append("header", header)
                .append("columnLength", columnLength)
                .append("delimiter", delimiter)
                .append("headerToSkip", headerToSkip)
                .append("footerToSkip", footerToSkip)
                .append("ratioGrade", ratioGrade)
                .append("threshold", threshold)
                .append("keyColumn", keyColumn)
                .append("requiredColumn", requiredColumn)
                .append("fileName", fileName)
                .append("formatGb", formatGb)
                .toString();
    }
}