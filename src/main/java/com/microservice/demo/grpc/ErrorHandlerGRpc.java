package com.microservice.demo.grpc;

import com.microservice.demo.configuration.exception.BusinessLogicException;
import io.grpc.Metadata;
import io.grpc.Status;
import org.lognet.springboot.grpc.recovery.GRpcExceptionHandler;
import org.lognet.springboot.grpc.recovery.GRpcExceptionScope;
import org.lognet.springboot.grpc.recovery.GRpcServiceAdvice;

@GRpcServiceAdvice
public class ErrorHandlerGRpc {

    @GRpcExceptionHandler
    public Status handle(BusinessLogicException exception, GRpcExceptionScope scope) {
        scope.getResponseHeaders().put(Metadata.Key.of("message", Metadata.ASCII_STRING_MARSHALLER), exception.getMessage());

        switch (exception.getCode()) {
            case BAD_REQUEST -> {
                return Status.INVALID_ARGUMENT.withCause(exception.getCause()).withDescription(exception.getMessage());
            }
            case NOT_FOUND, NO_CONTENT -> {
                return Status.NOT_FOUND.withCause(exception.getCause()).withDescription(exception.getMessage());
            }
            case FORBIDDEN -> {
                return Status.UNAUTHENTICATED.withCause(exception.getCause()).withDescription(exception.getMessage());
            }
            case SERVICE_UNAVAILABLE -> {
                return Status.UNAVAILABLE.withCause(exception.getCause()).withDescription(exception.getMessage());
            }
            default -> {
                return Status.INTERNAL.withCause(exception.getCause()).withDescription(exception.getMessage());
            }
        }
    }
}
