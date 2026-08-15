package org.edziennik.gradeservice.semester_grade.exception;

public class SemesterGradeNotFoundException extends RuntimeException {
    public SemesterGradeNotFoundException(String message) {
        super(message);
    }
}
