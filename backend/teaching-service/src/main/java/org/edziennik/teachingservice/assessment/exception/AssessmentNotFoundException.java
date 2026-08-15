package org.edziennik.teachingservice.assessment.exception;

import org.edziennik.teachingservice.exception.NotFoundException;

public class AssessmentNotFoundException extends NotFoundException {
    public AssessmentNotFoundException(String message) {
        super(message);
    }
}