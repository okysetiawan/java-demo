package com.microservice.demo.service;

import com.microservice.demo.dto.EmployeeDto;
import com.microservice.demo.entity.Employee;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EmployeeService {

    Mono<List<EmployeeDto>> getEmployees();

    Mono<EmployeeDto> getEmployeeById(Long id);

    Mono<EmployeeDto> createEmployee(EmployeeDto req);

    Mono<EmployeeDto> updateEmployee(EmployeeDto req);

    Mono<EmployeeDto> deleteEmployee(Long id);

}
