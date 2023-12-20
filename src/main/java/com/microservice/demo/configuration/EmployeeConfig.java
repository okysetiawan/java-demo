package com.microservice.demo.configuration;

import com.microservice.demo.service.EmployeeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeConfig {

    @Bean
    public EmployeeServiceImpl employeeBean() {
        return new EmployeeServiceImpl();
    }

}