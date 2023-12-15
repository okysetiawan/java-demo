package com.microservice.demo.controller;

import com.microservice.demo.dto.api.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlerController extends BaseErrorHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandlerController.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> runTimeException(RuntimeException re) {
        LOGGER.error("Runtime Error = {}", re.getMessage(), re);
        return ApiResponse.generateError(HttpStatus.INTERNAL_SERVER_ERROR, re.getMessage());
    }
}