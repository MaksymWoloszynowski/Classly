package org.edziennik.gradeservice.grade.exception;

import org.edziennik.gradeservice.exception.NotFoundException;

public class GradeNotFoundException extends NotFoundException {
    public GradeNotFoundException(String message) {
        super(message);
    }
}
