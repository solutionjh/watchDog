package com.nice.datafileanomalydetection.locale.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Controller
public class LocaleController {	
	@GetMapping(value = "/locale/{lang}")
	public String locale(HttpServletRequest request,HttpServletResponse response , @PathVariable(name = "lang") String lang) {
		Locale locale = new Locale(lang);
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setLocale(request, response, locale);
        String referer = request.getHeader("REFERER");
        return "redirect:"+ referer;
	}

}
