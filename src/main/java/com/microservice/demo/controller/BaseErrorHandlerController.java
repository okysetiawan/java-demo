package com.microservice.demo.controller;

import com.microservice.demo.common.CommonApiResponse;
import com.microservice.demo.configuration.exception.BusinessLogicException;
import com.microservice.demo.dto.api.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

public class BaseErrorHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseErrorHandlerController.class);

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> bindException(BindException be) {
        LOGGER.error("BindException = {}", be.getMessage(), be);
        List<FieldError> bindErrors = be.getFieldErrors();
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : bindErrors) {
            errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
        }

        return CommonApiResponse.createError(errors.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> exception(Exception e) {
        LOGGER.error("Exception = {}", e.getMessage(), e);
        return CommonApiResponse.createError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ApiResponse<Object> businessLogicException(BusinessLogicException ble) {
        LOGGER.error("BusinessLogicException = {}", ble.getMessage(), ble);
        return CommonApiResponse.createError(ble.getMessage(), ble.getCode());
    }

}
