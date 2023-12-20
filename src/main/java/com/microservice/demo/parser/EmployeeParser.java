package com.microservice.demo.parser;

import com.microservice.demo.dto.EmployeeDto;
import com.microservice.demo.entity.Employee;

public class EmployeeParser {
    public static Employee createFromDto(EmployeeDto employeeDto) {
        return Employee.builder().
                id(employeeDto.getId()).
                name(employeeDto.getName()).
                email(employeeDto.getEmail()).
                age(employeeDto.getAge()).
                build();
    }

    public static EmployeeDto createFromEntity(Employee employee) {
        return EmployeeDto.builder().
                id(employee.getId()).
                name(employee.getName()).
                email(employee.getEmail()).
                age(employee.getAge()).
                build();
    }
}
