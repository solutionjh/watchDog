package com.nice.datafileanomalydetection.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.nice.datafileanomalydetection.constant.LoginConstant;
import com.nice.datafileanomalydetection.login.service.LoginService;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler
{
	
	 @Autowired
	private  LoginService loginService;
	
	 @Value("${session.timeout}")
	 private int sessionTimeout;

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException 
	{
		
		//		로그인 성공 로그처리
       	request.getSession().setMaxInactiveInterval(sessionTimeout);
       	try {
			loginService.loginSession(request,authentication);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        response.sendRedirect(request.getContextPath()+LoginConstant.LOGIN_SUCCESS);
    }
}