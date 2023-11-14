package com.gebeya.pro.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gebeya.pro.Exception.ErrorMessage;

public class CustomerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CustomerException(String message) {
        super(message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    ErrorMessage exceptionHandler(RuntimeException e) {
        return new ErrorMessage(e.getMessage(), "400");
    }
}
