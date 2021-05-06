package com.nice.datafileanomalydetection.predict.service.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.lang.invoke.MethodHandles;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class JsonMapFieldExtractor<T> implements FieldExtractor<T>, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String floatingPointFormat;

    private String[] names;

    public void setFloatingPointFormat (String floatingPointFormat) {
        this.floatingPointFormat = floatingPointFormat;
    }

    public void setNames (String[] names) {
        Assert.notNull(names, "Names must be non-null");
        this.names = names.clone();
    }

    public Object[] extract (T item) {

        List<Object> values = new ArrayList<Object>();

        ConcurrentHashMap<String, Object> mapOutput = (ConcurrentHashMap<String, Object>) item;
        for (String name : this.names) {
            Object value = mapOutput.get(name);

            if (value instanceof Double || value instanceof Float) {
                DecimalFormat df = new DecimalFormat(this.floatingPointFormat);
                value = df.format(value);
            }

            values.add(value);

        }

//		for(String key : mapOutput.keySet()) {
//			Object value = mapOutput.get(key);
//			
//			if(value instanceof Double || value instanceof Float) {
//				DecimalFormat df = new DecimalFormat(this.floatingPointFormat);
//				value = df.format(value);
//			}
//			
//			values.add(value);
//		}


        return values.toArray();
    }


    public void afterPropertiesSet () {
        Assert.notNull(floatingPointFormat, "In json file writer, the floating point format must be set.");
    }


}
