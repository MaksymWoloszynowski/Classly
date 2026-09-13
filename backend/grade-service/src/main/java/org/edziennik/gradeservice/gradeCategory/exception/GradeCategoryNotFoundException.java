package org.edziennik.gradeservice.gradeCategory.exception;

import org.edziennik.gradeservice.exception.NotFoundException;

public class GradeCategoryNotFoundException extends NotFoundException {
    public GradeCategoryNotFoundException(String message) {
        super(message);
    }
}
