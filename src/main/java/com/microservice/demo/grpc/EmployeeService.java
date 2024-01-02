package com.microservice.demo.grpc;

import com.microservice.demo.EmployeeDetailResponse;
import com.microservice.demo.EmployeeServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class EmployeeService extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    // this is  still null
    @Autowired
    private com.microservice.demo.service.EmployeeService employeeService;

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
