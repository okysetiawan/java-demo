package com.microservice.demo.controller;

import com.microservice.demo.dto.EmployeeDto;
import com.microservice.demo.response.ResponseHandler;
import com.microservice.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/{id}")
    private ResponseEntity<Object> getEmployeeById(@PathVariable("id") int id) {
        EmployeeDto employee = employeeService.getEmployeeById(id);
        return ResponseHandler.generateResponse("ok", HttpStatus.OK, employee);
    }

}
