package com.nice.datafileanomalydetection.predict.model;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component("predictOnlineInfo")
public class PredictOnlineInfo {

    private ConcurrentHashMap<String, CopyOnWriteArrayList<ConcurrentHashMap<String, Double>>> staticSquaredErrorsMap = new ConcurrentHashMap<String, CopyOnWriteArrayList<ConcurrentHashMap<String, Double>>>();
    private ConcurrentHashMap<String, CopyOnWriteArrayList<ConcurrentHashMap<String, Double>>> staticItemSEProbsMap = new ConcurrentHashMap<String, CopyOnWriteArrayList<ConcurrentHashMap<String, Double>>>();
    private ConcurrentHashMap<String, String> modelClassNameMap = new ConcurrentHashMap<String, String>();
    private ConcurrentHashMap<String, Long> writeCntMap = new ConcurrentHashMap<String, Long>();
    private ConcurrentHashMap<String, Integer> modelItemCntMap = new ConcurrentHashMap<String, Integer>();
    private ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, Double>>> rowSquaredErrorInfosMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, Double>>>();
    private ConcurrentHashMap<String, Long> rowContainNullCntMap = new ConcurrentHashMap<String, Long>();
    private ConcurrentHashMap<String, Long> targetDataCntMap = new ConcurrentHashMap<String, Long>();
    private ConcurrentHashMap<String, Integer> nullItemCntMap = new ConcurrentHashMap<String, Integer>();


    public ConcurrentHashMap<String, CopyOnWriteArrayList<ConcurrentHashMap<String, Double>>> getStaticSquaredErrorsMap () {
        return staticSquaredErrorsMap;
    }

    public void setStaticSquaredErrorsMap (ConcurrentHashMap<String, CopyOnWriteArrayList<ConcurrentHashMap<String, Double>>> staticSquaredErrorsMap) {
        this.staticSquaredErrorsMap = staticSquaredErrorsMap;
    }

    public ConcurrentHashMap<String, CopyOnWriteArrayList<ConcurrentHashMap<String, Double>>> getStaticItemSEProbsMap () {
        return staticItemSEProbsMap;
    }

    public void setStaticItemSEProbsMap (ConcurrentHashMap<String, CopyOnWriteArrayList<ConcurrentHashMap<String, Double>>> staticItemSEProbsMap) {
        this.staticItemSEProbsMap = staticItemSEProbsMap;
    }

    public ConcurrentHashMap<String, String> getModelClassNameMap () {
        return modelClassNameMap;
    }

    public void setModelClassNameMap (ConcurrentHashMap<String, String> modelClassNameMap) {
        this.modelClassNameMap = modelClassNameMap;
    }

    public ConcurrentHashMap<String, Long> getWriteCntMap () {
        return writeCntMap;
    }

    public void setWriteCntMap (ConcurrentHashMap<String, Long> writeCntMap) {
        this.writeCntMap = writeCntMap;
    }

    public ConcurrentHashMap<String, Integer> getModelItemCntMap () {
        return modelItemCntMap;
    }


    public void setModelItemCntMap (ConcurrentHashMap<String, Integer> modelItemCntMap) {
        this.modelItemCntMap = modelItemCntMap;
    }


    public ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, Double>>> getRowSquaredErrorInfosMap () {
        return rowSquaredErrorInfosMap;
    }


    public void setRowSquaredErrorInfosMap (ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, Double>>> rowSquaredErrorInfosMap) {
        this.rowSquaredErrorInfosMap = rowSquaredErrorInfosMap;
    }

    public String getModelClassName (String projectName) {
        return this.modelClassNameMap.get(projectName);
    }

    public void setModelClassName (String projectName, String modelClassName) {
        this.modelClassNameMap.put(projectName, modelClassName);
    }

    public Long getWriteCnt (String projectName) {
        return this.writeCntMap.get(projectName);
    }

    public void setWriteCnt (String projectName, Long writeCnt) {
        this.writeCntMap.put(projectName, writeCnt);
    }

    public CopyOnWriteArrayList<ConcurrentHashMap<String, Double>> getStaticSquaredErrors (String projectName) {
        return this.staticSquaredErrorsMap.get(projectName);
    }

    public void setStaticSquaredErrors (String projectName, CopyOnWriteArrayList<ConcurrentHashMap<String, Double>> staticSquaredErrors) {
        this.staticSquaredErrorsMap.put(projectName, staticSquaredErrors);
    }

    public CopyOnWriteArrayList<ConcurrentHashMap<String, Double>> getStaticItemSEProbs (String projectName) {
        return this.staticItemSEProbsMap.get(projectName);
    }

    public void setStaticItemSEProbs (String projectName, CopyOnWriteArrayList<ConcurrentHashMap<String, Double>> staticItemSEProbs) {
        this.staticItemSEProbsMap.put(projectName, staticItemSEProbs);
    }

    public Integer getModelItemCnt (String projectName) {
        return this.modelItemCntMap.get(projectName);
    }

    public void setModelItemCnt (String projectName, Integer modelItemCnt) {
        this.modelItemCntMap.put(projectName, modelItemCnt);
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, Double>> getRowSquaredErrorInfos (String projectName) {
        return this.rowSquaredErrorInfosMap.get(projectName);
    }

    public void setRowSquaredErrorInfos (String projectName, ConcurrentHashMap<String, ConcurrentHashMap<String, Double>> rowSquaredErrorInfos) {
        this.rowSquaredErrorInfosMap.put(projectName, rowSquaredErrorInfos);
    }

    public ConcurrentHashMap<String, Long> getRowContainNullCntMap () {
        return rowContainNullCntMap;
    }

    public void setRowContainNullCntMap (ConcurrentHashMap<String, Long> rowContainNullCntMap) {
        this.rowContainNullCntMap = rowContainNullCntMap;
    }

    public Long getRowContainNullCnt (String projectName) {
        return this.rowContainNullCntMap.get(projectName);
    }

    public void setRowContainNullCnt (String projectName, Long nullRowCnt) {
        this.rowContainNullCntMap.put(projectName, nullRowCnt);
    }

    public ConcurrentHashMap<String, Long> getTargetDataCntMap () {
        return targetDataCntMap;
    }

    public void setTargetDataCntMap (ConcurrentHashMap<String, Long> targetDataCntMap) {
        this.targetDataCntMap = targetDataCntMap;
    }

    public Long getTargetDataCnt (String projectName) {
        return this.targetDataCntMap.get(projectName);
    }

    public void setTargetDataCnt (String projectName, Long targetDataCnt) {
        this.targetDataCntMap.put(projectName, targetDataCnt);
    }

    public ConcurrentHashMap<String, Integer> getNullItemCntMap () {
        return nullItemCntMap;
    }

    public void setNullItemCntMap (ConcurrentHashMap<String, Integer> nullItemCntMap) {
        this.nullItemCntMap = nullItemCntMap;
    }

    public Integer getNullItemCnt (String projectName) {
        return this.nullItemCntMap.get(projectName);
    }

    public void setNullItemCnt (String projectName, Integer nullItemCnts) {
        this.nullItemCntMap.put(projectName, nullItemCnts);
    }

    public void removeProjectData (String projectName) {
        this.staticSquaredErrorsMap.remove(projectName);
        this.staticItemSEProbsMap.remove(projectName);
        this.modelClassNameMap.remove(projectName);
        this.writeCntMap.remove(projectName);
        this.modelItemCntMap.remove(projectName);
        this.rowSquaredErrorInfosMap.remove(projectName);
        this.rowContainNullCntMap.remove(projectName);
        this.targetDataCntMap.remove(projectName);
        this.nullItemCntMap.remove(projectName);
    }

}
