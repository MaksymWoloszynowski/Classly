package org.classly.gradeservice.semesterGrade.exception;

public class SemesterGradeNotFoundException extends RuntimeException {
    public SemesterGradeNotFoundException(String message) {
        super(message);
    }
}
