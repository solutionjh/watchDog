package com.nice.datafileanomalydetection.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.nice.datafileanomalydetection.constant.LoginConstant;
import com.nice.datafileanomalydetection.message.ReloadMessageSource;


@Configuration
public class AuthFailurHandler implements AuthenticationFailureHandler  {
	
	@Autowired
	private ReloadMessageSource message;
	
	@Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException exception)
            throws IOException, ServletException {		
		
		req.setAttribute("error", exception.getMessage());
		
		if(exception instanceof BadCredentialsException) {			
			req.setAttribute("error", message.getMessage("err.login.002"));
		}
		
		//	로그인 실패 로그 처리
        req.getRequestDispatcher(LoginConstant.LOGIN_FAIL).forward(req, res);
    }
}
