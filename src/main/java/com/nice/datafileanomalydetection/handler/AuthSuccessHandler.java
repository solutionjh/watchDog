package com.nice.datafileanomalydetection.handler;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.nice.datafileanomalydetection.constant.LoginConstant;
import com.nice.datafileanomalydetection.login.service.LoginService;
import com.nice.datafileanomalydetection.member.service.MemberService;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler
{
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();
	
	 @Autowired
	 private  LoginService loginService;
	 @Autowired
	 private  MemberService memberService;
	
	 @Value("${session.timeout}")
	 private int sessionTimeout;

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException 
	{		
	
		//		로그인 성공 로그처리
       	request.getSession().setMaxInactiveInterval(sessionTimeout);
       	try {
			loginService.loginSession(request,authentication);
			memberService.updateMemberAccess(authentication.getName());
		} catch (Exception e) {
			logger.warn("onAuthenticationSuccess ::::: Login Session or Access Fail!!" );
		}
        response.sendRedirect(request.getContextPath()+LoginConstant.LOGIN_SUCCESS);
    }
}