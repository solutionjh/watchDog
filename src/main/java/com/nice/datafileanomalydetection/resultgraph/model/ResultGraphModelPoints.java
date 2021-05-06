/*
2020.03.18 kwb 
- watchDogResultGraph.jsp에서 scatter chart를 그리기위해 필요한 데이터(ResultGraphModelMseNRatio.java)의 리스트들을 저장
*/

package com.nice.datafileanomalydetection.resultgraph.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("resultgraphmodelpoints")
public class ResultGraphModelPoints {

    private List<ResultGraphModelMseNRatio> devMseNRatioList;    // 기준시점 데이터 리스트
    private List<ResultGraphModelMseNRatio> nowMseNRatioList;    // 현재시점 데이터 리스트

    public List<ResultGraphModelMseNRatio> getDevMseNRatioList () {
        return devMseNRatioList;
    }

    public void setDevMseNRatioList (List<ResultGraphModelMseNRatio> devMseNRatioList) {
        this.devMseNRatioList = devMseNRatioList;
    }

    public List<ResultGraphModelMseNRatio> getNowMseNRatioList () {
        return nowMseNRatioList;
    }

    public void setNowMseNRatioList (List<ResultGraphModelMseNRatio> nowMseNRatioList) {
        this.nowMseNRatioList = nowMseNRatioList;
    }
}
