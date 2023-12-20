package com.microservice.demo.service;

import com.microservice.demo.dto.EmployeeDto;
import reactor.core.publisher.Mono;

public interface EmployeeService {
    Mono<EmployeeDto> getEmployeeById(int id);
}
