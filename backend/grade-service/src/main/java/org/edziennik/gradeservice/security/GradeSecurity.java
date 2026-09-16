package org.edziennik.gradeservice.security;

import org.edziennik.gradeservice.grade.entity.Grade;
import org.edziennik.gradeservice.grade.repository.GradeRepository;
import org.edziennik.gradeservice.gradeCategory.entity.GradeCategory;
import org.edziennik.gradeservice.gradeCategory.exception.GradeCategoryNotFoundException;
import org.edziennik.gradeservice.gradeCategory.repository.GradeCategoryRepository;
import org.edziennik.gradeservice.grpc.SchoolStructureGrpcClient;
import org.edziennik.security.AuthenticatedUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component("gradeSecurity")
public class GradeSecurity {
    private final SchoolStructureGrpcClient schoolStructureClient;
    private final GradeRepository gradeRepository;
    private final GradeCategoryRepository gradeCategoryRepository;

    public GradeSecurity(SchoolStructureGrpcClient schoolStructureClient, GradeRepository gradeRepository,
                         GradeCategoryRepository gradeCategoryRepository) {
        this.schoolStructureClient = schoolStructureClient;
        this.gradeRepository = gradeRepository;
        this.gradeCategoryRepository = gradeCategoryRepository;
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

    public void requireGradeCategoryAccess(AuthenticatedUser user, UUID gradeCategoryId) {
        GradeCategory gradeCategory = gradeCategoryRepository.findById(gradeCategoryId)
                .orElseThrow(() -> new GradeCategoryNotFoundException("Grade category not found with ID: " + gradeCategoryId));
        requireAssignmentAccess(user, gradeCategory.getTeachingAssignmentId());
    }

    public void canEdit(AuthenticatedUser user, UUID gradeId) {
        Grade grade = gradeRepository.getReferenceById(gradeId);
        UUID teachingAssignmentId = grade.getGradeCategory().getTeachingAssignmentId();

        if (!requireTeacher(user).contains(teachingAssignmentId)) {
            throw new TeacherAccessDeniedException();
        }
    }
}
