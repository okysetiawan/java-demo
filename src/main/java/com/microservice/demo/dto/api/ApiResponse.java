package com.microservice.demo.dto.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> extends ResponseEntity<Object> {

    private final int status;
    private final String message;
    private final T data;

    public ApiResponse(int status, String message, T data, HttpStatus httpStatus) {
        super(data, httpStatus);
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static <T> ApiResponse<T> generateResponse(String message, HttpStatus status, T responseObj) {
        return new ApiResponse<>(status.value(), message, responseObj, status);
    }

    public static <T> ApiResponse<T> generateError(HttpStatus status, String message) {
        return new ApiResponse<>(status.value(), message, null, status);
    }
}