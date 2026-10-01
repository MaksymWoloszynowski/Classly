package org.classly.gradeservice.grpc;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.classly.schoolstructureservice.grpc.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolStructureGrpcClient {
    private final SchoolStructureServiceGrpc.SchoolStructureServiceBlockingStub blockingStub;

    public StudentResponse getStudent(UUID studentId) {
        StudentRequest request = StudentRequest.newBuilder().setStudentId(studentId.toString()).build();
        return this.blockingStub.getStudent(request);
    }

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

    public List<UUID> getStudentIdsForGroup(UUID groupId) {
        GroupRequest request = GroupRequest.newBuilder().setGroupId(groupId.toString()).build();
        StudentIdListResponse response = this.blockingStub.getStudentIdsByGroup(request);
        return response.getStudentIdsList().stream().map(UUID::fromString).collect(Collectors.toList());
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
                                .toList();
        }

    public TeachingAssignmentResponse getTeachingAssignment(UUID teachingAssignmentId) {
        TeachingAssignmentRequest request = TeachingAssignmentRequest.newBuilder()
                .setTeachingAssignmentId(teachingAssignmentId.toString())
                .build();
        return blockingStub.getTeachingAssignment(request);
    }

    public Map<UUID, TeachingAssignmentResponse> getTeachingAssignments(Set<UUID> teachingAssignmentsIds) {
        TeachingAssignmentIdsRequest request = TeachingAssignmentIdsRequest.newBuilder()
                .addAllTeachingAssignmentIds(
                        teachingAssignmentsIds.stream()
                                .map(UUID::toString)
                                .toList()
                )
                .build();

        TeachingAssignmentListResponse response = blockingStub.getTeachingAssignments(request);

        return response.getTeachingAssignmentsList().stream()
                .collect(Collectors.toMap(
                        assignment -> UUID.fromString(assignment.getId()),
                        Function.identity()
                ));
    }
}