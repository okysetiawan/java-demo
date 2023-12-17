package com.microservice.demo.service;

import com.microservice.demo.configuration.exception.BusinessLogicException;
import com.microservice.demo.dto.EmployeeDto;
import com.microservice.demo.entity.Employee;
import com.microservice.demo.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public Mono<EmployeeDto> getEmployeeById(int id) {
        return Mono.defer(() ->
                this.employeeRepo.findOneById(id).
                        map(employee -> new EmployeeDto(employee.getId(), employee.getName(), employee.getEmail(), employee.getAge()))
        ).switchIfEmpty(Mono.error(new BusinessLogicException(HttpStatus.BAD_REQUEST, "employee not found")));
    }
}
