package org.classly.gradeservice.semesterGrade.mapper;


import org.classly.gradeservice.semesterGrade.dto.SemesterGradeRequestDTO;
import org.classly.gradeservice.semesterGrade.dto.SemesterGradeResponseDTO;
import org.classly.gradeservice.semesterGrade.entity.SemesterGrade;

public class SemesterGradeMapper {
    public static SemesterGradeResponseDTO toDTO(SemesterGrade grade) {

        return SemesterGradeResponseDTO.builder()
                .id(grade.getId())
                .grade(grade.getGrade())
                .classificationPeriod(grade.getClassificationPeriod())
                .type(grade.getType())
                .studentId(grade.getStudentId())
                .teachingAssignmentId(grade.getTeachingAssignmentId())
                .build();
    }

    public static SemesterGrade toModel(SemesterGradeRequestDTO gradeDTO) {
        return SemesterGrade.builder()
                .grade(gradeDTO.getGrade())
                .type(gradeDTO.getType())
                .classificationPeriod(gradeDTO.getClassificationPeriod())
                .studentId(gradeDTO.getStudentId())
                .teachingAssignmentId(gradeDTO.getTeachingAssignmentId())
                .build();
    }
}
