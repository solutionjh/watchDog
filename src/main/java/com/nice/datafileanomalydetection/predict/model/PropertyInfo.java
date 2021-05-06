package com.nice.datafileanomalydetection.predict.model;

import org.springframework.stereotype.Component;

import java.util.Properties;

@Component("propertyInfo")
public class PropertyInfo {

    private String fixedLayoutYN;
    private String header;
    private String columnLength;
    private String delimiter;
    private String headerToSkip;
    private String footerToSkip;

    private String ratiograde;
    private String threshold;

    private String keyColumn;
    private String requiredColumn;

    private String projectName;

    public PropertyInfo () {

    }

    public PropertyInfo (Properties properties) {
        this.fixedLayoutYN = properties.getProperty("fixedlayoutyn");
        this.header = properties.getProperty("header");
        this.columnLength = properties.getProperty("columnlength");
        this.delimiter = properties.getProperty("delimeter");
        this.headerToSkip = properties.getProperty("headertoskip");
        this.footerToSkip = properties.getProperty("footertoskip");
        this.ratiograde = properties.getProperty("ratiograde");
        this.threshold = properties.getProperty("threshold");
        this.keyColumn = properties.getProperty("keycolumn");
        this.requiredColumn = properties.getProperty("requiredcolumn");
    }

    public String getFixedLayoutYN () {
        return fixedLayoutYN;
    }

    public void setFixedLayoutYN (String fixedLayoutYN) {
        this.fixedLayoutYN = fixedLayoutYN;
    }

    public String getHeader () {
        return header;
    }

    public void setHeader (String header) {
        this.header = header;
    }

    public String getColumnLength () {
        return columnLength;
    }

    public void setColumnLength (String columnLength) {
        this.columnLength = columnLength;
    }

    public String getDelimiter () {
        return delimiter;
    }

    public void setDelimiter (String delimiter) {
        this.delimiter = delimiter;
    }

    public String getHeaderToSkip () {
        return headerToSkip;
    }

    public void setHeaderToSkip (String headerToSkip) {
        this.headerToSkip = headerToSkip;
    }

    public String getFooterToSkip () {
        return footerToSkip;
    }

    public void setFooterToSkip (String footerToSkip) {
        this.footerToSkip = footerToSkip;
    }

    public String getRatiograde () {
        return ratiograde;
    }

    public void setRatiograde (String ratiograde) {
        this.ratiograde = ratiograde;
    }

    public String getThreshold () {
        return threshold;
    }

    public void setThreshold (String threshold) {
        this.threshold = threshold;
    }

    public String getKeyColumn () {
        return keyColumn;
    }

    public void setKeyColumn (String keyColumn) {
        this.keyColumn = keyColumn;
    }

    public String getRequiredColumn () {
        return requiredColumn;
    }

    public void setRequiredColumn (String requiredColumn) {
        this.requiredColumn = requiredColumn;
    }

    public String getProjectName () {
        return projectName;
    }

    public void setProjectName (String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("PropertyInfo [");
        if (fixedLayoutYN != null) {
            builder.append("fixedLayoutYN=");
            builder.append(fixedLayoutYN);
            builder.append(", ");
        }
        if (header != null) {
            builder.append("header=");
            builder.append(header);
            builder.append(", ");
        }
        if (columnLength != null) {
            builder.append("columnLength=");
            builder.append(columnLength);
            builder.append(", ");
        }
        if (delimiter != null) {
            builder.append("delimiter=");
            builder.append(delimiter);
            builder.append(", ");
        }
        if (headerToSkip != null) {
            builder.append("headerToSkip=");
            builder.append(headerToSkip);
            builder.append(", ");
        }
        if (footerToSkip != null) {
            builder.append("footerToSkip=");
            builder.append(footerToSkip);
            builder.append(", ");
        }
        if (ratiograde != null) {
            builder.append("ratiograde=");
            builder.append(ratiograde);
            builder.append(", ");
        }
        if (threshold != null) {
            builder.append("threshold=");
            builder.append(threshold);
            builder.append(", ");
        }
        if (keyColumn != null) {
            builder.append("keyColumn=");
            builder.append(keyColumn);
            builder.append(", ");
        }
        if (requiredColumn != null) {
            builder.append("requiredColumn=");
            builder.append(requiredColumn);
            builder.append(", ");
        }
        if (projectName != null) {
            builder.append("projectName=");
            builder.append(projectName);
        }
        builder.append("]");
        return builder.toString();
    }

}
