package org.edziennik.gradeservice.semester_grade.mapper;


import org.edziennik.gradeservice.semester_grade.dto.SemesterGradeRequestDTO;
import org.edziennik.gradeservice.semester_grade.dto.SemesterGradeResponseDTO;
import org.edziennik.gradeservice.semester_grade.entity.SemesterGrade;

public class SemesterGradeMapper {
    public static SemesterGradeResponseDTO toDTO(SemesterGrade grade) {

        return SemesterGradeResponseDTO.builder()
                .id(grade.getId())
                .grade(grade.getGrade())
                .type(grade.getType())
                .schoolYear(grade.getSchoolYear())
                .studentId(grade.getStudentId())
                .subjectId(grade.getSubjectId())
                .build();
    }

    public static SemesterGrade toModel(SemesterGradeRequestDTO gradeDTO) {
        return SemesterGrade.builder()
                .grade(gradeDTO.getGrade())
                .type(gradeDTO.getType())
                .schoolYear(gradeDTO.getSchoolYear())
                .studentId(gradeDTO.getStudentId())
                .subjectId(gradeDTO.getSubjectId())
                .build();
    }
}
