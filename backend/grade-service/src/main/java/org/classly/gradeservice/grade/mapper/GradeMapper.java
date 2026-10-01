package org.classly.gradeservice.grade.mapper;

import org.classly.gradeservice.grade.dto.GradeRequestDTO;
import org.classly.gradeservice.grade.dto.GradeResponseDTO;
import org.classly.gradeservice.grade.dto.GradeSummaryDTO;
import org.classly.gradeservice.grade.entity.Grade;
import org.classly.gradeservice.gradeCategory.entity.GradeCategory;

import java.time.LocalDate;

public class GradeMapper {
    public static GradeResponseDTO toDTO(Grade grade) {

        return GradeResponseDTO.builder()
                .id(grade.getId())
                .categoryId(grade.getGradeCategory().getId())
                .grade(grade.getGrade())
                .date(grade.getDate())
                .description(grade.getGradeCategory().getDescription())
                .type(grade.getGradeCategory().getType())
                .weight(grade.getGradeCategory().getWeight())
                .build();
    }

    public static Grade toModel(GradeRequestDTO gradeDTO, GradeCategory gradeCategory) {
        return Grade.builder()
                .gradeCategory(gradeCategory)
                .grade(gradeDTO.getGrade())
                .date(LocalDate.now())
                .studentId(gradeDTO.getStudentId())
                .build();
    }

    public static GradeSummaryDTO toSummaryDTO(Grade grade) {
        return GradeSummaryDTO.builder()
                .id(grade.getId())
                .grade(grade.getGrade())
                .date(grade.getDate())
                .studentId(grade.getStudentId())
                .build();
    }
}
