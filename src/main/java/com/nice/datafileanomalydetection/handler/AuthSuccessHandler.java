package com.nice.datafileanomalydetection.handler;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import com.nice.datafileanomalydetection.login.service.LoginService;
import com.nice.datafileanomalydetection.member.service.MemberService;

@Configuration
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final Boolean isDebugEnabled = logger.isDebugEnabled();
	
	 @Autowired
	 private  LoginService loginService;
	 @Autowired
	 private  MemberService memberService;
	
	 @Value("${session.timeout}")
	 private int sessionTimeout;

	private RequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		SavedRequest savedRequest = this.requestCache.getRequest(request, response);
		
		//		로그인 성공 로그처리
	   	request.getSession().setMaxInactiveInterval(sessionTimeout);
	   	try {
			loginService.loginSession(request,authentication);
			memberService.updateMemberAccess(authentication.getName());
		} catch (Exception e) {
			logger.warn("onAuthenticationSuccess ::::: Login Session or Access Fail!!" );
		}
				
	   	
	   	System.out.println("authentication::::::"+authentication.getAuthorities());
	   	
		if (savedRequest == null) {
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}
		String targetUrlParameter = getTargetUrlParameter();
		if (isAlwaysUseDefaultTargetUrl()
				|| (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
			this.requestCache.removeRequest(request, response);
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}
		clearAuthenticationAttributes(request);
		// Use the DefaultSavedRequest URL
		String targetUrl = savedRequest.getRedirectUrl();
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}

}