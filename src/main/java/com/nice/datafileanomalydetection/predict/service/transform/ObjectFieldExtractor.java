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

package com.nice.datafileanomalydetection.predict.service.transform;

import com.nice.datafileanomalydetection.predict.service.core.ReflectionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * item에 담긴 정보들을 getter 호출을 통해 추출하여 Object 배열로 반환하는 클래스
 *
 * @author NICE Information Solution Team
 * @version 1.0
 * @see <pre>
 *      개정이력(Modification Information)
 *
 *   수정일      수정자           수정내용
 *  ------- -------- ---------------------------
 *  2015.03.07  ChangdaeCho     최초 생성
 *  </pre>
 * @since 2015.03.07
 */

public class ObjectFieldExtractor<T> implements FieldExtractor<T>, InitializingBean {

    // slf4J logger 로 변경 : 2014.04.30
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // xml에 설정 된 extract할 VO의 field names
    private String[] names;

    // ReflectionSupport 사용을 위한 변수
    private ReflectionSupport<T> reflection;

    /**
     * @param names xml에 설정 된 extract할 VO의 field names
     */
    public void setNames (String[] names) {
        Assert.notNull(names, "Names must be non-null");
        this.names = names.clone();
    }

    /**
     * item에 담긴 field 정보를 추출하여 Object 배열로 return
     *
     * @see org.springframework.batch.item.file.transform.FieldExtractor#extract(java.lang.Object)
     */
    public Object[] extract (T item) {

        List<Object> values = new ArrayList<Object>();
        try {
            reflection.generateGetterMethodMap(names, item);
        } catch (Exception e) {
            // slf4J logger 로 변경 : 2014.04.30
            logger.debug("debug", e);
        }

        for (int i = 0; i < names.length; i++) {
            values.add(reflection.invokeGettterMethod(item, names[i]));
        }
        return values.toArray();
    }

    /**
     * bean이 등록 될 때 실행
     */
    public void afterPropertiesSet () {
        Assert.notNull(names, "The 'names' property must be set.");
        reflection = new ReflectionSupport<T>();
    }
}
