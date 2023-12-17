package com.microservice.demo.configuration.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class BusinessLogicException extends RuntimeException {

    @Getter
    private HttpStatus code;
    private String message;

    public BusinessLogicException(HttpStatus code, String message) {
        super();
        this.setCode(code);
        this.setMessage(message);
    }

    public BusinessLogicException(HttpStatus responseCode) {
        super();
        this.setCode(responseCode);
        this.setMessage(responseCode.name());
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "BusinessLogicException{" + "code='" + code + '\'' + "} " + super.toString();
    }
}
