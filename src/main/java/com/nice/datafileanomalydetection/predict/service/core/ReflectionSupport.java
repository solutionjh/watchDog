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
package com.nice.datafileanomalydetection.predict.service.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Reflection 관련 method
 *
 * @author NICE Information Solution Team
 * @version 1.0
 * @see <pre>
 *      개정이력(Modification Information)
 *
 *   수정일      수정자           수정내용
 *  ------- -------- ---------------------------
 *  2015.03.07  ChangdaeCho     최초 생성
 *  2019.01.30  김정훈          불필요한 Try-catch 제거
 * </pre>
 * @since 2015.03.07
 */
public class ReflectionSupport<T> {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // 실제 VO가 담길 Object
    private Object object = null;

    // VO에 들어있는 Method 목록
    private Method[] methods;

    // VO의 Field명과 Method 목록의 Index를 매핑
    private ConcurrentHashMap<String, Method> methodMap;

    // VO의 Field 타입
    private Type[] fieldType;


    public ReflectionSupport () {
        super();
    }

    /**
     * VO에 들어있는 Method 목록 get
     */
    public ConcurrentHashMap<String, Method> getMethodMap () {
        return methodMap;
    }

    /**
     * 주어진 Method 이름(methodName)으로  Method를 검색한다.
     *
     * @param methods
     * @param methodName
     * @return Method
     */
    private Method retrieveMethod (Method[] methods, String methodName) {
        Method method = null;

        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodName)) {
                method = methods[i];
                break;
            }
        }

        return method;
    }

    /**
     * VO 타입의 instance 생성
     *
     * @param type VO 타입
     */
    private void createObject (Class<?> type) {
        try {
            object = type.newInstance();
        } catch (InstantiationException e) {
            ReflectionUtils.handleReflectionException(e);
        } catch (IllegalAccessException e) {
            ReflectionUtils.handleReflectionException(e);
        }
    }


    /**
     * VO의 Field명과 Setter Method들을 비교하여 Map에 Put 한다.
     * Bean 생성시 한 번만 실행 된다.
     */
    public void generateSetterMethodMap (Class<?> type, String[] names) throws Exception {
        methods = type.newInstance().getClass().getMethods();
        methodMap = new ConcurrentHashMap<String, Method>();

        for (int i = 0; i < names.length; i++) {
            try {
                String strMethod = "set" + (names[i].substring(0, 1)).toUpperCase() + names[i].substring(1);
                methodMap.put(names[i], retrieveMethod(methods, strMethod));
            } catch (Exception e) {
                throw new Exception("Cannot create a method list of given : " + type);
            }
        }
    }


    /**
     * VO의 Setter method 실행
     *
     * @param tokens VO에 set 될 value
     * @param names  VO의 field 명
     */
    private void invokeSetterMethod (List<String> tokens, String[] names) {
        Method method;

        for (int i = 0; i < names.length; i++) {
            method = methodMap.get(names[i]);
            try {
                method.invoke(object, parsingFromString(tokens.get(i).trim(), fieldType[i]));
            } catch (IllegalAccessException e) {
                ReflectionUtils.handleReflectionException(e);
            } catch (InvocationTargetException e) {
                ReflectionUtils.handleReflectionException(e);
            } catch (SecurityException e) {
                ReflectionUtils.handleReflectionException(e);
            }
        }
    }


    @SuppressWarnings("unchecked")
    public T generateObject (Class<?> type, List<String> tokens, String[] names) {
        createObject(type);
        invokeSetterMethod(tokens, names);

        return (T) object;
    }

    /**
     * VO의 Field명과 Getter Method들을 비교하여 Map에 Put 한다.
     * Bean 생성시 한 번만 실행 된다.
     */
    public void generateGetterMethodMap (String[] names, T item) throws Exception {
        if (methods == null) {
            methods = item.getClass().getMethods();
            methodMap = new ConcurrentHashMap<String, Method>();
            try {
                for (int i = 0; i < names.length; i++) {
                    String strMethod = "get" + (names[i].substring(0, 1)).toUpperCase() + names[i].substring(1);
                    methodMap.put(names[i], retrieveMethod(methods, strMethod));
                }
            } catch (Exception e) {
                throw new Exception("Cannot create a method list of given : " + item);
            }
        }
    }


    /**
     * item의 field 정보를 가져오기 위해 getter를 invoke
     *
     * @param item  정보를 담고 있는 VO
     * @param names VO의 field name
     * @return getValue field 정보를 get 하여 return
     */
    public Object invokeGettterMethod (T item, String names) {
        Object value = null;

        try {
            value = methodMap.get(names).invoke(item);
        } catch (IllegalArgumentException e) {
            ReflectionUtils.handleReflectionException(e);
        } catch (IllegalAccessException e) {
            ReflectionUtils.handleReflectionException(e);
        } catch (InvocationTargetException e) {
            ReflectionUtils.handleReflectionException(e);
        }
        return value;
    }

    /**
     * item의 field 정보를 가져오기 위해 getter를 invoke
     *
     * @param item      item 정보를 담고 있는 VO
     * @param param
     * @param mapMethod VO의 getter 메소드를 갖고 있는 Map
     * @return field 정보를 get 하여 return
     */
    public Object invokeGettterMethod (T item, String param, Map<String, Method> mapMethod) {
        Object value = null;

        try {
            value = mapMethod.get(param).invoke(item);
        } catch (IllegalArgumentException e) {
            ReflectionUtils.handleReflectionException(e);
        } catch (IllegalAccessException e) {
            ReflectionUtils.handleReflectionException(e);
        } catch (InvocationTargetException e) {
            ReflectionUtils.handleReflectionException(e);
        }
        return value;

    }

    /**
     * VO의 field의 타입 get
     *
     * @param type  VO의 타입
     * @param names VO field names
     */
    public void getFieldType (Class<?> type, String[] names) {
        fieldType = new Type[names.length];
        for (int i = 0; i < names.length; i++) {
            try {
                fieldType[i] = type.newInstance().getClass().getDeclaredField(names[i]).getType();
            } catch (SecurityException e) {
                ReflectionUtils.handleReflectionException(e);
            } catch (NoSuchFieldException e) {
                ReflectionUtils.handleReflectionException(e);
            } catch (InstantiationException e) {
                ReflectionUtils.handleReflectionException(e);
            } catch (IllegalAccessException e) {
                ReflectionUtils.handleReflectionException(e);
            }
        }
    }


    /**
     * sqlType을 배열 형식으로 받는 메소드
     */
    public String[] getSqlTypeArray (String[] params, Object item) {

        String[] sqlTypes = new String[params.length];

        for (int i = 0; i < params.length; i++) {

            try {
                sqlTypes[i] = item.getClass().getDeclaredField(params[i]).getType().getSimpleName();

            } catch (SecurityException e) {
                ReflectionUtils.handleReflectionException(e);
            } catch (NoSuchFieldException e) {
                ReflectionUtils.handleReflectionException(e);
            }
        }
        return sqlTypes;

    }

    /**
     * String to Type parsing
     *
     * @param tokenValue String 형태의 token value
     * @param type       실제 VO에서 token value의 type
     * @return parsingValue 해당 type으로 paring한 value return
     */
    private Object parsingFromString (String tokenValue, Type type) {
        Object parsingValue = null;

        if (type == String.class) {
            parsingValue = tokenValue;
        } else if (type == int.class) {
            parsingValue = Integer.parseInt(tokenValue);
        } else if (type == double.class) {
            parsingValue = Double.parseDouble(tokenValue);
        } else if (type == float.class) {
            parsingValue = Float.parseFloat(tokenValue);
        } else if (type == long.class) {
            parsingValue = Long.parseLong(tokenValue);
        } else if (type == char.class) {
            parsingValue = tokenValue.charAt(0);
        } else if (type == byte[].class) {
            parsingValue = tokenValue.getBytes();
        } else if (type == boolean.class) {
            parsingValue = Boolean.valueOf(tokenValue);
        } else if (type == BigDecimal.class) {
            parsingValue = new BigDecimal(tokenValue);
        } else {
            parsingValue = tokenValue;
        }

        return parsingValue;
    }

}
