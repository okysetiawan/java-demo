package com.microservice.demo.controller;

import com.microservice.demo.response.ResponseHandler;
import com.microservice.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/{id}")
    private Mono<ResponseEntity<Object>> getEmployeeById(@PathVariable("id") int id) {
        return employeeService.getEmployeeById(id).
                map(employeeDto -> ResponseHandler.generateResponse("ok", HttpStatus.OK, employeeDto));
    }

}
