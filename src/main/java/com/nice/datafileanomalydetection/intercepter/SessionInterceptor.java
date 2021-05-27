package com.nice.datafileanomalydetection.intercepter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.nice.datafileanomalydetection.util.SessionUtils;

@Component
public class SessionInterceptor implements HandlerInterceptor{
	@Value("${session.timeout}")
	 private int sessionTimeout;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if(SessionUtils.isLogin()) request.getSession().setMaxInactiveInterval(sessionTimeout);			
		
		return true;
	}
}