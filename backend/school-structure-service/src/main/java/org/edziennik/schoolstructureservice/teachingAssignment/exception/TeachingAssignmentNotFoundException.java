package org.edziennik.schoolstructureservice.teachingAssignment.exception;

import org.edziennik.schoolstructureservice.exception.NotFoundException;

public class TeachingAssignmentNotFoundException extends NotFoundException {
    public TeachingAssignmentNotFoundException(String message) {
        super(message);
    }
}