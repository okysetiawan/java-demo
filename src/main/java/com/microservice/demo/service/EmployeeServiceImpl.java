package com.microservice.demo.service;

import com.microservice.demo.configuration.exception.BusinessLogicException;
import com.microservice.demo.dto.EmployeeDto;
import com.microservice.demo.entity.Employee;
import com.microservice.demo.parser.EmployeeParser;
import com.microservice.demo.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        return validateExisted(req)
                .switchIfEmpty(this.employeeRepo.save(EmployeeParser.createFromDto(req)))
                .map(EmployeeParser::createFromEntity);
    }

    private Mono<Employee> validateExisted(EmployeeDto req) {
        return this.employeeRepo.findOneByEmail(req.getEmail())
                .flatMap(existingEmployee -> {
                    if (!Objects.equals(existingEmployee.getId(), req.getId())) {
                        return Mono.error(new BusinessLogicException(HttpStatus.BAD_REQUEST, "Please use another email"));
                    }
                    return Mono.empty();
                });
    }

    public Mono<EmployeeDto> updateEmployee(EmployeeDto req) {
        return this.validateExisted(req)
                .then(this.employeeRepo.findOneById(req.getId()))
                .flatMap(existingEmployee -> {
                    if (Objects.isNull(existingEmployee)) {
                        return Mono.error(new BusinessLogicException(HttpStatus.NOT_FOUND, "employee not found"));
                    }
                    existingEmployee.setAge(req.getAge());
                    existingEmployee.setName(req.getName());
                    return this.employeeRepo.save(existingEmployee);
                })
                .map(EmployeeParser::createFromEntity);
    }

    public Mono<EmployeeDto> deleteEmployee(Long id) {
        return this.employeeRepo.findOneById(id)
                .switchIfEmpty(Mono.error(new BusinessLogicException(HttpStatus.NOT_FOUND, "Employee not found")))
                .flatMap(existingEmployee -> this.employeeRepo.deleteOneById(id).
                        then(Mono.just(EmployeeParser.createFromEntity(existingEmployee))));
    }
}
