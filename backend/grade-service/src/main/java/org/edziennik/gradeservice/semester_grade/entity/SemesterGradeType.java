package org.edziennik.gradeservice.semester_grade.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Type of a semester or annual grade")
public enum SemesterGradeType {
    PROPOSED_SEMESTER, FINAL_SEMESTER, PROPOSED_ANNUAL, FINAL_ANNUAL
}