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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * MapFieldSetMapper 클래스
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
public class MapFieldSetMapper<T> implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // VO type 지정
    private Class<? extends T> type;

    // names(필드들)지정
    private String[] names;

    // json 용 key 항목 Array 지정
//	private String[] keys;

    /**
     * VO type을 설정한다.
     *
     * @param type
     */
    public void setType (Class<? extends T> type) {
        this.type = type;
    }

    /**
     * names를 설정한다.
     *
     * @return names
     */
    public String[] getNames () {
        return this.names;
    }

    /**
     * names를 설정한다.
     *
     * @param names
     */
    public void setNames (String[] names) {
        this.names = names;
        Assert.notNull(names, "Names must be non-null");
    }

    /**
     * VO를 만들고, Token을 세팅한다.
     *
     * @param tokens: LineTokenizer에서 line을 자른 token
     * @return T: VO
     */
    @SuppressWarnings("unchecked")
    public T mapObject (List<String> tokens) {
        int tokenSize = tokens.size();

        // 지정된 names(필드들)의 수와  실제 만들어 낸 Token 갯수가 다르면 예외를 던진다.
        if (names.length != tokenSize) {
            throw new IncorrectTokenCountException(names.length, tokenSize);
        }

        ConcurrentHashMap<String, Object> mapInput = new ConcurrentHashMap<String, Object>();

        for (int i = 0; i < names.length; i++) {

            mapInput.put(names[i], tokens.get(i).trim());

        }

        return (T) mapInput;
    }

    // refs #1283 json용 mapObject 구현 (2020-06-08, kty)
    @SuppressWarnings("unchecked")
    public T mapObject (Map<String, String> tokens) {
        int tokenSize = tokens.size();

//		if (names.length + keys.length != tokenSize) {
//			throw new IncorrectTokenCountException(names.length, tokenSize);
//		}

        // json 처리시에는 미리 항목수 세팅을 하지 않으므로 항목수 체크하지 않음
        //Map<String, Object> mapInput = Collections.synchronizedMap(new LinkedHashMap<String, Object>());
        //Map<String, Object> mapInput = new LinkedHashMap<String, Object>();
        ConcurrentHashMap<String, Object> mapInput = new ConcurrentHashMap<String, Object>();

//		for(int i = 0 ; i < keys.length ; i ++) {
//			mapInput.put(keys[i], tokens.get(keys[i]));
//		}

        for (Map.Entry<String, String> elem : tokens.entrySet()) {
            mapInput.put(elem.getKey(), elem.getValue());
        }

        return (T) mapInput;
    }

    public void afterPropertiesSet () throws Exception {
        Assert.notNull(names, "The names must be set");
    }
}
