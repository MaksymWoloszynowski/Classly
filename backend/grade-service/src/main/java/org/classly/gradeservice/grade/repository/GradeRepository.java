package org.classly.gradeservice.grade.repository;

import org.classly.gradeservice.grade.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface GradeRepository extends JpaRepository<Grade, UUID>, JpaSpecificationExecutor<Grade> {
    List<Grade> findByStudentIdAndDateBetween(UUID studentId, LocalDate from, LocalDate to);
}
