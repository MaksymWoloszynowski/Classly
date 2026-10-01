package org.classly.scheduleservice.grpc;

import lombok.RequiredArgsConstructor;
import org.classly.schoolstructureservice.grpc.GroupRequest;
import org.classly.schoolstructureservice.grpc.SchoolStructureServiceGrpc;
import org.classly.schoolstructureservice.grpc.SubjectRequest;
import org.classly.schoolstructureservice.grpc.SubjectResponse;
import org.classly.schoolstructureservice.grpc.TeacherRequest;
import org.classly.schoolstructureservice.grpc.TeacherResponse;
import org.classly.schoolstructureservice.grpc.TeachingAssignmentIdListResponse;
import org.classly.schoolstructureservice.grpc.TeachingAssignmentRequest;
import org.classly.schoolstructureservice.grpc.TeachingAssignmentResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolStructureGrpcClient {
    private final SchoolStructureServiceGrpc.SchoolStructureServiceBlockingStub blockingStub;

    public TeachingAssignmentResponse getGrpcTeachingAssignment(UUID teachingAssignmentId) {
        TeachingAssignmentRequest request = TeachingAssignmentRequest.newBuilder().setTeachingAssignmentId(teachingAssignmentId.toString()).build();
        return blockingStub.getTeachingAssignment(request);
    }

    public SubjectResponse getGrpcSubject(UUID subjectId) {
        SubjectRequest request = SubjectRequest.newBuilder().setSubjectId(subjectId.toString()).build();
        return blockingStub.getSubject(request);
    }

    public TeacherResponse getGrpcTeacher(UUID teacherId) {
        TeacherRequest request = TeacherRequest.newBuilder().setTeacherId(teacherId.toString()).build();
        return blockingStub.getTeacher(request);
    }

    public List<UUID> getTeachingAssignmentIdsByGroup(UUID groupId) {
        GroupRequest request = GroupRequest.newBuilder().setGroupId(groupId.toString()).build();
        TeachingAssignmentIdListResponse response = blockingStub.getTeachingAssignmentIdsByGroup(request);

        return response.getTeachingAssignmentIdsList().stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());
    }

    public List<UUID> getTeachingAssignmentIdsByTeacher(UUID teacherId) {
        TeacherRequest request = TeacherRequest.newBuilder().setTeacherId(teacherId.toString()).build();
        TeachingAssignmentIdListResponse response = blockingStub.getTeachingAssignmentIdsByTeacher(request);

        return response.getTeachingAssignmentIdsList().stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());
    }

    public Map<UUID, TeachingAssignmentResponse> getTeachingAssignments(Set<UUID> ids) {
        return ids.stream().collect(Collectors.toMap(id -> id, this::getGrpcTeachingAssignment));
    }
}