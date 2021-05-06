/*
2020.03.18 kwb 
- 테이블 각 행의 mse값과 해당 mse의 ratio값을 저장
- watchDogResultGraph.jsp에서 scatter chart를 그릴 때, mse값을 x좌표, ratio값을 y좌표로 사용
*/

package com.nice.datafileanomalydetection.resultgraph.model;

import org.springframework.stereotype.Component;

@Component("resultgraphmsenratio")
public class ResultGraphModelMseNRatio {

    private String mse;
    private String ratio;

    public String getMse () {
        return mse;
    }

    public void setMse (String mse) {
        this.mse = mse;
    }

    public String getRatio () {
        return ratio;
    }

    public void setRatio (String ratio) {
        this.ratio = ratio;
    }
}
