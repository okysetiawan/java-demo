package com.microservice.demo.service;

import com.microservice.demo.dto.EmployeeDto;
import com.microservice.demo.entity.Employee;
import com.microservice.demo.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public EmployeeDto getEmployeeById(int id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent()) {
            EmployeeDto response = new EmployeeDto();
            response.setAge(employee.get().getAge());
            response.setEmail(employee.get().getEmail());
            response.setName(employee.get().getName());
            response.setId(employee.get().getId());
            return response;
        } else {
            return null;
        }
    }
}
