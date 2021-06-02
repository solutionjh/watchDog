package com.nice.datafileanomalydetection.error;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController  {
	
	public static final String VIEW_PATH_404 = "/content/error/404";
	public static final String VIEW_PATH_500 = "/content/error/500";
	public static final String VIEW_PATH_ACCESS_DENIED = "/content/error/accessDenied";
	   
	@RequestMapping(value = "/error")
	public String handleError(HttpServletRequest request , HttpServletResponse response) throws IOException {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				if(!"XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
					return VIEW_PATH_404;
				}
			}
			if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				if(!"XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
					return VIEW_PATH_500;
				}
			}
		}
		return "error";
	}
	
	@GetMapping(value = "/accessDenied")
	public String accessDenied(HttpServletRequest request ) {
		return VIEW_PATH_ACCESS_DENIED;
	}
	@GetMapping(value = "/noPage")
	public String noPage(HttpServletRequest request ) {
		return VIEW_PATH_404;
	}
	@GetMapping(value = "/serverError")
	public String serverError(HttpServletRequest request ) {
		return VIEW_PATH_500;
	}
}
