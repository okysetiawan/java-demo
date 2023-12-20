package com.microservice.demo.common;

import com.microservice.demo.dto.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonApiResponse {

    public CommonApiResponse() {

    }

    public static <T> ApiResponse<T> createError(String message, HttpStatus status) {
        return new ApiResponse<>(status, message, null);
    }

    public static <T> ApiResponse<T> createResponse(HttpStatus status, T data) {
        return new ApiResponse<>(status, "OK", data);
    }

    public static <T> ApiResponse<T> createResponse(T data) {
        return new ApiResponse<>(HttpStatus.OK, "OK", data);
    }
}
