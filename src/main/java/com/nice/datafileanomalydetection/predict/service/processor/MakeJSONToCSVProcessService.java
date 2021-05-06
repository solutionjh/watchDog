package com.nice.datafileanomalydetection.predict.service.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.Map;


@Component("makeJSONToCSVProcess")
@StepScope
public class MakeJSONToCSVProcessService implements ItemProcessor<Map<String, Object>, Map<String, Object>> {

    protected static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("#{jobParameters['inputItemList']}")
    private String inputItemList;
    @Value("#{jobParameters['keyItems']}")
    private String keyItems;
    @Value("#{jobParameters['targetKeys']}")
    private String targetKeys;

    @Override
    public Map<String, Object> process (Map<String, Object> map) throws Exception {

        String[] keyItemArray = keyItems.split(",", - 1);
        String[] targetKeyArray = targetKeys.split(",", - 1);

        // refs #1283 지정된 pkg, snst, aplydtim 에 대한 데이터만 csv로 변환하도록 itemprocessor 에 조건 추가
        boolean isKeyMatched = true;
        for (int i = 0; i < keyItemArray.length; i++) {
            if (! targetKeyArray[i].equals(map.get(keyItemArray[i]))) {
                isKeyMatched = false;
                break;
            }
            map.remove(keyItemArray[i]);
        }

        if (! isKeyMatched) {
            return null;
        }

        // zero padding 삭제
        for (Map.Entry<String, Object> elem : map.entrySet()) {
            map.put(elem.getKey(), elem.getValue().toString().replaceFirst("^0+(?!$)", ""));
        }

        return map;
    }

}

