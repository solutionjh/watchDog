package com.nice.datafileanomalydetection.result.model;

import org.jetbrains.annotations.NotNull;

public class ItemAnomalyLevel implements Comparable<ItemAnomalyLevel> {
    String fieldName;
    double itemAnomalyLevel;
    String isAnomaly;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public double getItemAnomalyLevel() {
        return itemAnomalyLevel;
    }

    public void setItemAnomalyLevel(double itemAnomalyLevel) {
        this.itemAnomalyLevel = itemAnomalyLevel;
    }

    public String getIsAnomaly() {
        return isAnomaly;
    }

    public void setIsAnomaly(String isAnomaly) {
        this.isAnomaly = isAnomaly;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ItemAnomalyLevel{");
        sb.append("fieldName='").append(fieldName).append('\'');
        sb.append(", itemAnomalyLevel=").append(itemAnomalyLevel);
        sb.append(", isAnomaly='").append(isAnomaly).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(@NotNull ItemAnomalyLevel itemAnomalyLevel) {
        return -Double.compare(this.itemAnomalyLevel, itemAnomalyLevel.getItemAnomalyLevel());
    }
}
