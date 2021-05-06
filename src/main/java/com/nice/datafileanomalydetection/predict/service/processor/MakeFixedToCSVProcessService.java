package com.nice.datafileanomalydetection.predict.service.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.Map;

@Component("makeFixedToCSVProcess")
public class MakeFixedToCSVProcessService implements ItemProcessor<Map<String, Object>, Map<String, Object>> {

    protected static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Map<String, Object> process (Map<String, Object> map) throws Exception {

        return map;
    }

}

