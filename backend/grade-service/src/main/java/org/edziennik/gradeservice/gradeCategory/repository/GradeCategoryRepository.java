package org.edziennik.gradeservice.gradeCategory.repository;

import org.edziennik.gradeservice.gradeCategory.entity.GradeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GradeCategoryRepository extends JpaRepository<GradeCategory, UUID> {
    List<GradeCategory> findByTeachingAssignmentId(UUID teachingAssignmentId);

    List<GradeCategory> findByTeachingAssignmentIdAndClassificationPeriod(
            UUID teachingAssignmentId,
            UUID classificationPeriod
    );
}

