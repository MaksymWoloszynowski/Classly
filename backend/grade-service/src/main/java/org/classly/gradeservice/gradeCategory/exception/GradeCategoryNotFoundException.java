package org.classly.gradeservice.gradeCategory.exception;

import org.classly.gradeservice.exception.NotFoundException;

public class GradeCategoryNotFoundException extends NotFoundException {
    public GradeCategoryNotFoundException(String message) {
        super(message);
    }
}
