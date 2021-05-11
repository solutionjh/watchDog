package com.nice.datafileanomalydetection.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.nice.datafileanomalydetection.constant.LoginConstant;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler
{
	
	 @Value("${session.timeout}")
	 private int sessionTimeout;

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException 
	{
		//		로그인 성공 로그처리
       	request.getSession(false).setMaxInactiveInterval(sessionTimeout);
        response.sendRedirect(request.getContextPath()+LoginConstant.LOGIN_SUCCESS);
    }
}