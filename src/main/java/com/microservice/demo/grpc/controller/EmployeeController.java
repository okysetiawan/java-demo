package com.microservice.demo.grpc.controller;

import com.microservice.demo.EmployeeDetailResponse;
import com.microservice.demo.EmployeeServiceGrpc;
import com.microservice.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;


public class EmployeeController extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    private EmployeeService employeeService;

    @Override
    public void detail(com.microservice.demo.EmployeeDetailRequest request,
                       io.grpc.stub.StreamObserver<com.microservice.demo.EmployeeDetailResponse> responseObserver) {
        this.employeeService.getEmployeeById(request.getId())
                .map(employeeDto -> EmployeeDetailResponse.newBuilder()
                        .setAge(employeeDto.getAge())
                        .setEmail(employeeDto.getEmail())
                        .setId(employeeDto.getId()).
                        build())
                .subscribe(responseObserver::onNext, responseObserver::onError, responseObserver::onCompleted);
    }
}
