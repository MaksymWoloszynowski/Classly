package org.edziennik.gradeservice.grade.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Type of an individual grade")
public enum GradeType {
    CURRENT, QUIZ, HOMEWORK, TEST, CLASS_TEST, PARTICIPATION
}