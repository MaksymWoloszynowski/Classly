package org.classly.schoolstructureservice.teachingAssignment.exception;

import org.classly.schoolstructureservice.exception.NotFoundException;

public class TeachingAssignmentNotFoundException extends NotFoundException {
    public TeachingAssignmentNotFoundException(String message) {
        super(message);
    }
}