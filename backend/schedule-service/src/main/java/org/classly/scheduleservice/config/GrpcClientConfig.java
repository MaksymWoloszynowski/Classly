package org.classly.scheduleservice.config;

import org.classly.schoolstructureservice.grpc.SchoolStructureServiceGrpc;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.ImportGrpcClients;

@Configuration
@ImportGrpcClients(
        target = "schoolStructure",
        types = SchoolStructureServiceGrpc.SchoolStructureServiceBlockingStub.class
)
public class GrpcClientConfig {
}