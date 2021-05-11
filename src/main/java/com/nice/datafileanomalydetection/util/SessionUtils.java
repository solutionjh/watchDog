package com.nice.datafileanomalydetection.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.nice.datafileanomalydetection.login.model.Login;

public class SessionUtils {
	
	 /**
//     * HttpSession에 등록된 정보를 가져온다.
//     *
//     * @param key keyName
//     * @return
//     */
//	
	private static String LOGIN_INFO = "LOGIN_INFO";
    
    /**
     * HttpSession에 로그인한 사용자 ROLE 정보를 담은 VO를 가져 온다.
     *
     * @return UserVO - 로그인한 사용자 ROLE 정보를 담은 VO
     */
    public static Login getLogin() {
        return  (Login) RequestContextHolder.currentRequestAttributes().getAttribute(LOGIN_INFO, RequestAttributes.SCOPE_SESSION);
    }
           
    public static void setLogin(Login login) {
    	RequestContextHolder.currentRequestAttributes().setAttribute(LOGIN_INFO, login, RequestAttributes.SCOPE_SESSION);
    }
    
    public static void removeLogin() {
    	RequestContextHolder.currentRequestAttributes().removeAttribute(LOGIN_INFO, RequestAttributes.SCOPE_SESSION);
    }
    
    
    /**
     * HttpSession에 로그인한 여부를 가져 온다.
     *
     * @return boolean - 로그인한 여부
     */
    public static boolean isLogin() {
    	return SessionUtils.getLogin() == null ? false : true;
    }
   
}
