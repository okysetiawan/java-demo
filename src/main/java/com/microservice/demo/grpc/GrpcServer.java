package com.microservice.demo.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GrpcServer implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        Server server = ServerBuilder.forPort(51001)
                .addService(new EmployeeGRpcService())
                .build();

        server.start();
        server.awaitTermination();
    }

    @Bean
    public GrpcServer grpcServerRunner() {
        return new GrpcServer();
    }
}
