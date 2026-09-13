package org.edziennik.scheduleservice.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.edziennik.scheduleservice.schedule.entity.Schedule;
import org.edziennik.schoolstructureservice.grpc.*;
import org.edziennik.schoolstructureservice.grpc.GroupRequest;
import org.edziennik.schoolstructureservice.grpc.SchoolStructureServiceGrpc;
import org.edziennik.schoolstructureservice.grpc.SubjectRequest;
import org.edziennik.schoolstructureservice.grpc.SubjectResponse;
import org.edziennik.schoolstructureservice.grpc.TeacherRequest;
import org.edziennik.schoolstructureservice.grpc.TeacherResponse;
import org.edziennik.schoolstructureservice.grpc.TeachingAssignmentIdListResponse;
import org.edziennik.schoolstructureservice.grpc.TeachingAssignmentRequest;
import org.edziennik.schoolstructureservice.grpc.TeachingAssignmentResponse;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SchoolStructureGrpcClient {
    @GrpcClient("school-structure-service")
    private SchoolStructureServiceGrpc.SchoolStructureServiceBlockingStub blockingStub;

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