/*
 * Copyright 2012-2014 MOSPA(Ministry of Security and Public Administration).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nice.datafileanomalydetection.predict.service.mapping;

import com.nice.datafileanomalydetection.predict.service.transform.LineTokenizer;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;


/**
 * MapLineMapper 클래스
 *
 * @author NICE Information Solution Team
 * @version 1.0
 * @see <pre>
 *      개정이력(Modification Information)
 *
 *   수정일      수정자           수정내용
 *  ------- -------- ---------------------------
 *  2015.05.15  ChangdaeCho     최초 생성
 * </pre>
 * @since 2015.05.15
 */
public class MapLineMapper<T> implements LineMapper<T>, InitializingBean {

    //String Line을 token들로 만들 LineTokenizer
    private LineTokenizer<T> tokenizer;

    //Token들을 VO로 변환할 ObjectMapper
    private MapFieldSetMapper<T> mapper;

    private int totalItemsToRead;

    public void setTotalItemsToRead (int totalItemsToRead) {
        this.totalItemsToRead = totalItemsToRead;
    }


    /**
     * String line을 String Array로 tokenize 한 다음, Object 형태의 VO로 만들어준다.
     *
     * @param line
     * @param lineNumber
     */
    public T mapLine (String line, int lineNumber) throws Exception {
        if (lineNumber > totalItemsToRead) {
            return null;
        }
        return mapper.mapObject(tokenizer.tokenize(line));
    }

    /**
     * LineTokenizer를 세팅한다.
     *
     * @param tokenizer
     */
    public void setLineTokenizer (LineTokenizer<T> tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * ObjectMapper를 세팅한다.
     *
     * @param objectMapper
     */
    public void setObjectMapper (MapFieldSetMapper<T> objectMapper) {
        this.mapper = objectMapper;
    }

    public void afterPropertiesSet () {
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(mapper, "The ObjectMapper must be set");
    }
}

