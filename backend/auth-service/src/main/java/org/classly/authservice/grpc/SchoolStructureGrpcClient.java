package org.classly.authservice.grpc;

import lombok.RequiredArgsConstructor;
import org.classly.schoolstructureservice.grpc.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolStructureGrpcClient {
    private final SchoolStructureServiceGrpc.SchoolStructureServiceBlockingStub blockingStub;

    public Map<UUID, StudentResponse> getStudents(Set<UUID> studentIds) {
        StudentIdsRequest request = StudentIdsRequest.newBuilder()
                .addAllStudentIds(
                        studentIds.stream()
                                .map(UUID::toString)
                                .toList()
                )
                .build();

        StudentListResponse response = blockingStub.getStudents(request);

        return response.getStudentsList().stream()
                .collect(Collectors.toMap(
                        student -> UUID.fromString(student.getId()),
                        Function.identity()
                ));
    }

    public Map<UUID, TeacherResponse> getTeachers(Set<UUID> teacherIds) {
        TeacherIdsRequest request = TeacherIdsRequest.newBuilder()
                .addAllTeacherIds(
                        teacherIds.stream()
                                .map(UUID::toString)
                                .toList()
                )
                .build();

        TeacherListResponse response = blockingStub.getTeachers(request);

        return response.getTeacherList().stream()
                .collect(Collectors.toMap(
                        teacher -> UUID.fromString(teacher.getId()),
                        Function.identity()
                ));
    }

    public Map<UUID, ParentResponse> getParents(Set<UUID> parentIds) {
        ParentIdsRequest request = ParentIdsRequest.newBuilder()
                .addAllParentIds(
                        parentIds.stream()
                                .map(UUID::toString)
                                .toList()
                )
                .build();

        ParentListResponse response = blockingStub.getParents(request);

        return response.getParentsList().stream()
                .collect(Collectors.toMap(
                        parent -> UUID.fromString(parent.getId()),
                        Function.identity()
                ));
    }
}