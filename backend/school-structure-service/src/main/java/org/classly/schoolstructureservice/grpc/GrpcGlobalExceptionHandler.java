package org.classly.schoolstructureservice.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.grpc.server.advice.GrpcAdvice;
import org.springframework.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class GrpcGlobalExceptionHandler {

    @GrpcExceptionHandler(IllegalArgumentException.class)
    public StatusRuntimeException handleIllegalArgumentException(
            IllegalArgumentException ex
    ) {
        return Status.INVALID_ARGUMENT
                .withDescription("Invalid request: " + ex.getMessage())
                .asRuntimeException();
    }

    @GrpcExceptionHandler(Exception.class)
    public StatusRuntimeException handleException(Exception ex) {
        return Status.INTERNAL
                .withDescription("Internal server error")
                .asRuntimeException();
    }
}