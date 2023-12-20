package com.microservice.demo.service;

import com.microservice.demo.configuration.exception.BusinessLogicException;
import com.microservice.demo.dto.EmployeeDto;
import com.microservice.demo.entity.Employee;
import com.microservice.demo.parser.EmployeeParser;
import com.microservice.demo.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public Mono<List<EmployeeDto>> getEmployees() {
        return this.employeeRepo.findAll(Sort.by("id").descending())
                .collectList()
                .flatMap(employees -> {
                    List<EmployeeDto> response = new ArrayList<>();
                    employees.forEach(employee -> response.add(EmployeeParser.createFromEntity(employee)));
                    return Mono.just(response);
                });
    }

    public Mono<EmployeeDto> getEmployeeById(Long id) {
        return Mono.defer(() ->
                this.employeeRepo.findOneById(id).
                        map(employee -> new EmployeeDto(employee.getId(), employee.getName(), employee.getEmail(), employee.getAge()))
        ).switchIfEmpty(Mono.error(new BusinessLogicException(HttpStatus.BAD_REQUEST, "employee not found")));
    }

    public Mono<EmployeeDto> createEmployee(EmployeeDto req) {
        return this.employeeRepo.findOneByEmail(req.getEmail())
                .flatMap(existingEmployee -> {
                    if (!ObjectUtils.isEmpty(existingEmployee)) {
                        return Mono.error(new BusinessLogicException(HttpStatus.BAD_REQUEST, "Please use another email"));
                    }

                    Employee employee = EmployeeParser.createFromDto(req);
                    return this.employeeRepo.save(employee);
                })
                .switchIfEmpty(this.employeeRepo.save(EmployeeParser.createFromDto(req)))
                .map(EmployeeParser::createFromEntity);
    }


    public Mono<EmployeeDto> updateEmployee(EmployeeDto req) {
        return Mono.defer(() -> this.employeeRepo.findOneById(req.getId()))
                .switchIfEmpty(Mono.error(new BusinessLogicException(HttpStatus.NOT_FOUND, "Employee not found")))
                .flatMap(existingEmployee -> this.employeeRepo.save(existingEmployee))
                .map(EmployeeParser::createFromEntity);
    }

    public Mono<EmployeeDto> deleteEmployee(Long id) {
        return this.employeeRepo.findOneById(id)
                .switchIfEmpty(Mono.error(new BusinessLogicException(HttpStatus.NOT_FOUND, "Employee not found")))
                .flatMap(existingEmployee -> this.employeeRepo.deleteOneById(id).
                        then(Mono.just(EmployeeParser.createFromEntity(existingEmployee))));
    }
}
