package com.microservice.demo.controller;

import com.microservice.demo.common.CommonApiResponse;
import com.microservice.demo.dto.EmployeeDto;
import com.microservice.demo.dto.api.ApiResponse;
import com.microservice.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping(value = "")
    private Mono<ApiResponse<List<EmployeeDto>>> getEmployees() {
        return employeeService.getEmployees()
                .map(employeeDto -> CommonApiResponse.createResponse(employeeDto));
    }

    @GetMapping(value = "/{id}")
    private Mono<ApiResponse<EmployeeDto>> getEmployeeById(@PathVariable("id") Long id) {
        return employeeService.getEmployeeById(id)
                .map(CommonApiResponse::createResponse);
    }

    @PostMapping(value = "")
    private Mono<ApiResponse<EmployeeDto>> createEmployee(@RequestBody EmployeeDto id) {
        return employeeService.createEmployee(id)
                .map(CommonApiResponse::createResponse);
    }

    @PutMapping(value = "/{id}")
    private Mono<ApiResponse<EmployeeDto>> updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeDto req) {
        req.setId(id);
        return employeeService.updateEmployee(req)
                .map(CommonApiResponse::createResponse);
    }

    @DeleteMapping(value = "/{id}")
    private Mono<ApiResponse<EmployeeDto>> deleteEmployee(@PathVariable("id") Long id) {
        return employeeService.deleteEmployee(id)
                .map(CommonApiResponse::createResponse);
    }

}
