package com.nice.datafileanomalydetection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nice.datafileanomalydetection.constant.LoginConstant;
import com.nice.datafileanomalydetection.intercepter.SessionInterceptor;


@Configuration
public class AuthWebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	SessionInterceptor sessionInterceptor;
	
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {		
		//세션 시간 갱신
		registry.addInterceptor(sessionInterceptor).excludePathPatterns(
				LoginConstant.SCHEMA,LoginConstant.ASSETS,LoginConstant.WEBJARS,
				LoginConstant.LOGIN,LoginConstant.LOGIN_PROCESS,LoginConstant.ACCESS_DENIED,
				LoginConstant.LOGOUT,LoginConstant.H2CONSOLE
				);

    }
	
}
