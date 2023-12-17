package com.microservice.demo.controller;

import com.microservice.demo.common.CommonApiResponse;
import com.microservice.demo.dto.EmployeeDto;
import com.microservice.demo.dto.api.ApiResponse;
import com.microservice.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private Mono<ApiResponse<EmployeeDto>> getEmployeeById(@PathVariable("id") int id) {
        return employeeService.getEmployeeById(id)
                .map(employeeDto -> CommonApiResponse.createResponse(HttpStatus.OK, employeeDto));
    }

}
