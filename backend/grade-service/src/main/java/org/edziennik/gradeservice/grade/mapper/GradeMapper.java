package org.edziennik.gradeservice.grade.mapper;

import org.edziennik.gradeservice.grade.dto.GradeRequestDTO;
import org.edziennik.gradeservice.grade.dto.GradeResponseDTO;
import org.edziennik.gradeservice.grade.entity.Grade;

import java.time.LocalDate;

public class GradeMapper {
    public static GradeResponseDTO toDTO(Grade grade) {

        return GradeResponseDTO.builder()
                .id(grade.getId())
                .grade(grade.getGrade())
                .date(grade.getDate())
                .description(grade.getDescription())
                .classificationPeriod(grade.getClassificationPeriod())
                .type(grade.getType())
                .weight(grade.getWeight())
                .studentId(grade.getStudentId())
                .subjectId(grade.getSubjectId())
                .build();
    }

    public static Grade toModel(GradeRequestDTO gradeDTO) {
        return Grade.builder()
                .grade(gradeDTO.getGrade())
                .date(LocalDate.now())
                .description(gradeDTO.getDescription())
                .classificationPeriod(gradeDTO.getClassificationPeriod())
                .type(gradeDTO.getType())
                .weight(gradeDTO.getWeight())
                .studentId(gradeDTO.getStudentId())
                .subjectId(gradeDTO.getSubjectId())
                .build();
    }
}
