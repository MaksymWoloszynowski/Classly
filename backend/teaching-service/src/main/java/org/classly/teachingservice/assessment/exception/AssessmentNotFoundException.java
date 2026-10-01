package org.classly.teachingservice.assessment.exception;

import org.classly.teachingservice.exception.NotFoundException;

public class AssessmentNotFoundException extends NotFoundException {
    public AssessmentNotFoundException(String message) {
        super(message);
    }
}