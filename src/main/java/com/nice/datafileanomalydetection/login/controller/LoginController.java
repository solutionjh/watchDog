package com.nice.datafileanomalydetection.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {	
	@GetMapping(value = "/login")
	public String view(HttpServletRequest request ,HttpServletResponse response) {
		return "content/login";
	}

	@PostMapping(value = "/loginFail")
	public String loginFail(HttpServletRequest request ) {
		return "content/login";
	}
}
