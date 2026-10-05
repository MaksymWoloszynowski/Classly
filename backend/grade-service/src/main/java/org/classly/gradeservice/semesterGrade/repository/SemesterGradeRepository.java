package org.classly.gradeservice.semesterGrade.repository;

import org.classly.gradeservice.semesterGrade.entity.SemesterGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SemesterGradeRepository extends JpaRepository<SemesterGrade, UUID>, JpaSpecificationExecutor<SemesterGrade> {
}