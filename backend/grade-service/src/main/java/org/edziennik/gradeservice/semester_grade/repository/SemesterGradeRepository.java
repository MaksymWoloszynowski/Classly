package org.edziennik.gradeservice.semester_grade.repository;

import org.edziennik.gradeservice.semester_grade.entity.SemesterGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SemesterGradeRepository extends JpaRepository<SemesterGrade, UUID> {
    List<SemesterGrade> findByStudentId(UUID studentId);

    List<SemesterGrade> findByStudentIdAndSchoolYear(UUID studentId, String schoolYear);
    List<SemesterGrade> findByStudentIdAndSubjectIdAndSchoolYear(UUID studentId, UUID subjectId, String schoolYear);

    List<SemesterGrade> findByStudentIdAndType(UUID studentId, String type);
}