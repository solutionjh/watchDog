package com.nice.datafileanomalydetection;

import java.lang.invoke.MethodHandles;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RestController
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@ResponseBody
	@ExceptionHandler(value=Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleGlobalException(Exception e){
		logger.error(ExceptionUtils.getStackTrace(e));
		return ExceptionUtils.getMessage(e);
	}
}
