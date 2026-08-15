package org.edziennik.teachingservice.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.edziennik.schoolstructureservice.grpc.*;
import org.edziennik.schoolstructureservice.grpc.SchoolStructureServiceGrpc;
import org.edziennik.schoolstructureservice.grpc.StudentRequest;
import org.edziennik.schoolstructureservice.grpc.StudentResponse;
import org.edziennik.schoolstructureservice.grpc.TeachingAssignmentRequest;
import org.edziennik.schoolstructureservice.grpc.TeachingAssignmentResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class SchoolStructureGrpcClient {
    @GrpcClient("school-structure-service")
    private SchoolStructureServiceGrpc.SchoolStructureServiceBlockingStub blockingStub;

    public TeachingAssignmentResponse getTeachingAssignment(UUID teachingAssignmentId) {
        TeachingAssignmentRequest request = TeachingAssignmentRequest.newBuilder()
                .setTeachingAssignmentId(teachingAssignmentId.toString())
                .build();
        return blockingStub.getTeachingAssignment(request);
    }

    public StudentResponse getStudent(UUID studentId) {
        StudentRequest request = StudentRequest.newBuilder().setStudentId(studentId.toString()).build();
        return blockingStub.getStudent(request);
    }

    public Map<UUID, String> getStudentNames(Set<UUID> studentIds) {
        Map<UUID, String> result = new HashMap<>();
        for (UUID id : studentIds) {
            StudentResponse r = getStudent(id);
            result.put(id, r.getFirstName() + " " + r.getLastName());
        }
        return result;
    }
}