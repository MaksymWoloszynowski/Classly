package org.classly.gradeservice.grade.exception;

import org.classly.gradeservice.exception.NotFoundException;

public class GradeNotFoundException extends NotFoundException {
    public GradeNotFoundException(String message) {
        super(message);
    }
}
