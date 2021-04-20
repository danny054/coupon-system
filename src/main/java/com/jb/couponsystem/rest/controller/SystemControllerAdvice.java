package com.jb.couponsystem.rest.controller;

import com.jb.couponsystem.rest.ex.InvalidLoginException;
import com.jb.couponsystem.rest.ex.SystemMalfunctionException;
import com.jb.couponsystem.rest.model.SystemErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SystemControllerAdvice {

    @ExceptionHandler(InvalidLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SystemErrorResponse handleUnauthorized(InvalidLoginException ex) {
        return SystemErrorResponse.ofNow(ex.getMessage());
    }

    @ExceptionHandler(SystemMalfunctionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SystemErrorResponse handleFailed(SystemMalfunctionException ex) {
        return SystemErrorResponse.ofNow(ex.getMessage());
    }
}
