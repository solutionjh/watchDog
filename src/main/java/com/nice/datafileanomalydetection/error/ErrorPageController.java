package com.nice.datafileanomalydetection.error;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController  {
	
	public static final String VIEW_PATH_404 = "/content/error/404";
	public static final String VIEW_PATH_500 = "/content/error/500";
	
	@RequestMapping(value = "/error")
	public String handleError(HttpServletRequest request , HttpServletResponse response) throws IOException {
		
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return VIEW_PATH_404;
			}
			if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return VIEW_PATH_500;
			}
		}
		return "error";
	}

	@Override
	public String getErrorPath() {
		return null;
	}
}
