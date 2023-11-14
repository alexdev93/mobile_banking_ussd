package com.gebeya.pro.Exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ControllerExceptionHandler {

	@ResponseBody
	@ExceptionHandler(RuntimeException.class)
	ErrorMessage exceptionHandler(RuntimeException e) {
		return new ErrorMessage(e.getMessage(), "401");
	}
	
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ErrorMessage exceptionHandler(MethodArgumentNotValidException e) {
		return new ErrorMessage(e.getMessage(), "405");
	}
}