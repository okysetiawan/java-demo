package com.microservice.demo.repository;

import com.microservice.demo.entity.Employee;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface EmployeeRepo extends R2dbcRepository<Employee, Integer> {
    Mono<Employee> findOneById(Integer id);
}
