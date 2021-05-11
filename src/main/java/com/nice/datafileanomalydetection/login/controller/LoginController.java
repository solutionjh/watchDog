package com.nice.datafileanomalydetection.login.controller;

import java.lang.invoke.MethodHandles;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nice.datafileanomalydetection.login.service.LoginService;

import io.swagger.annotations.Api;

@Api(value="로그인")
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
	private  LoginService loginService;
	
	
	@GetMapping(value = "/login")
	public String view(HttpServletRequest request ) {
		System.out.println("=================login===========");
		return "content/login";
	}

	@GetMapping(value = "/loginSuccess")
	public String login(HttpServletRequest request , Authentication auth) throws Exception {		
		loginService.loginSession(request,auth);
		System.out.println("=================loginSuccess===========");
		return "content/home";
	}
	@PostMapping(value = "/loginFail")
	public String test(HttpServletRequest request ) {
		return "content/login";
	}
	@PostMapping(value = "/accessDenied")
	public String accessDenied(HttpServletRequest request ) {
		return "content/login";
	}


}
