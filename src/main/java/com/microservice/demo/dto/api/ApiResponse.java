package com.microservice.demo.dto.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ApiResponse<T> extends ResponseEntity<T> {

    private final int status;
    private final String message;
    private final T data;

    public ApiResponse(int status, String message, T data, HttpStatus httpStatus) {
        super(data, httpStatus);
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> generateResponse(HttpStatus status, T responseObj) {
        return new ApiResponse<>(status.value(), "OK", responseObj, status);
    }

    public static <T> ApiResponse<T> generateError(HttpStatus status, String message) {
        return new ApiResponse<>(status.value(), message, null, status);
    }
}
