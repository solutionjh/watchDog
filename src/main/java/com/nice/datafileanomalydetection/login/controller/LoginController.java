package com.nice.datafileanomalydetection.login.controller;

import java.lang.invoke.MethodHandles;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nice.datafileanomalydetection.login.service.LoginService;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
	private  LoginService loginService;
	
	
	@GetMapping(value = "/login")
	public String view(HttpServletRequest request ) {
		return "content/login";
	}

	@PostMapping(value = "/loginFail")
	public String loginFail(HttpServletRequest request ) {
		return "content/login";
	}
	@PostMapping(value = "/accessDenied")
	public String accessDenied(HttpServletRequest request ) {
		return "content/login";
	}


}
