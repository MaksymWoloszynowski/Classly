package org.edziennik.teachingservice.security;

import org.edziennik.security.AuthenticatedUser;
import org.edziennik.teachingservice.grpc.SchoolStructureGrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeacherAccessService {
    private final SchoolStructureGrpcClient schoolStructureClient;

    public TeacherAccessService(SchoolStructureGrpcClient schoolStructureClient) {
        this.schoolStructureClient = schoolStructureClient;
    }

    public List<UUID> requireTeacher(AuthenticatedUser user) {
        if (user == null || !"ROLE_TEACHER".equals(user.role())) {
            throw new TeacherAccessDeniedException();
        }
        return schoolStructureClient.getTeachingAssignmentIdsByTeacher(user.refId());
    }

    public void requireAssignmentAccess(AuthenticatedUser user, UUID teachingAssignmentId) {
        if (!requireTeacher(user).contains(teachingAssignmentId)) {
            throw new TeacherAccessDeniedException();
        }
    }
}
