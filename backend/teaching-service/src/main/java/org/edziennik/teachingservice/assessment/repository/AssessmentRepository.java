package org.edziennik.teachingservice.assessment.repository;

import org.edziennik.teachingservice.assessment.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AssessmentRepository extends JpaRepository<Assessment, UUID>, JpaSpecificationExecutor<Assessment> {
    List<Assessment> findByTeachingAssignmentId(UUID teachingAssignmentId);
    List<Assessment> findByTeachingAssignmentIdInAndDateDueBetween(List<UUID> teachingAssignmentId, LocalDate from, LocalDate to);
}