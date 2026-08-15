package org.edziennik.gradeservice.grade.repository;

import org.edziennik.gradeservice.grade.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GradeRepository extends JpaRepository<Grade, UUID>, JpaSpecificationExecutor<Grade> {

    List<Grade> findBySubjectIdAndStudentIdIn(UUID subjectId, List<UUID> studentIds);
}
