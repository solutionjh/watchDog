package com.nice.datafileanomalydetection.predict.service.mapping;

import com.nice.datafileanomalydetection.predict.service.transform.JsonLineTokenizer;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class JsonMapLineMapper<T> implements LineMapper<T>, InitializingBean {

    private JsonLineTokenizer tokenizer;

    private MapFieldSetMapper<T> mapper;

    public T mapLine (String line, int lineNumber) {
        return mapper.mapObject(tokenizer.doTokenizeJson(line));
    }


    public void setLineTokenizer (JsonLineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }


    public void setObjectMapper (MapFieldSetMapper<T> objectMapper) {
        this.mapper = objectMapper;
    }


    public void afterPropertiesSet () {
        Assert.notNull(tokenizer, "JSON Line Tokenizer must be set");
        Assert.notNull(mapper, "JSON ObjectMapper must be set");
    }

}
