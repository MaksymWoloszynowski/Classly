//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.edziennik.gradeservice.grpc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.edziennik.schoolstructureservice.grpc.GroupRequest;
import org.edziennik.schoolstructureservice.grpc.SchoolStructureServiceGrpc;
import org.edziennik.schoolstructureservice.grpc.StudentIdListResponse;
import org.edziennik.schoolstructureservice.grpc.StudentRequest;
import org.edziennik.schoolstructureservice.grpc.StudentResponse;
import org.edziennik.schoolstructureservice.grpc.SubjectRequest;
import org.edziennik.schoolstructureservice.grpc.SubjectResponse;
import org.springframework.stereotype.Component;

@Component
public class SchoolStructureGrpcClient {
    @GrpcClient("school-structure-service")
    private SchoolStructureServiceGrpc.SchoolStructureServiceBlockingStub blockingStub;

    public StudentResponse getStudent(UUID studentId) {
        StudentRequest request = StudentRequest.newBuilder().setStudentId(studentId.toString()).build();
        return this.blockingStub.getStudent(request);
    }

    public SubjectResponse getSubject(UUID subjectId) {
        SubjectRequest request = SubjectRequest.newBuilder().setSubjectId(subjectId.toString()).build();
        return this.blockingStub.getSubject(request);
    }

    public Map<UUID, String> getStudentNames(Set<UUID> studentIds) {
        Map<UUID, String> result = new HashMap();

        for(UUID id : studentIds) {
            StudentResponse r = this.getStudent(id);
            String var10002 = r.getFirstName();
            result.put(id, var10002 + " " + r.getLastName());
        }

        return result;
    }

    public Map<UUID, String> getSubjectNames(Set<UUID> subjectIds) {
        Map<UUID, String> result = new HashMap();

        for(UUID id : subjectIds) {
            result.put(id, this.getSubject(id).getName());
        }

        return result;
    }

    public List<UUID> getStudentIdsForGroup(UUID groupId) {
        GroupRequest request = GroupRequest.newBuilder().setGroupId(groupId.toString()).build();
        StudentIdListResponse response = this.blockingStub.getStudentIdsByGroup(request);
        return (List)response.getStudentIdsList().stream().map(UUID::fromString).collect(Collectors.toList());
    }
}