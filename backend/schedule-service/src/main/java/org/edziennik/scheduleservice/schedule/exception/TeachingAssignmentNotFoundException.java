package org.edziennik.scheduleservice.schedule.exception;

import org.edziennik.scheduleservice.exception.NotFoundException;

import java.util.UUID;

public class TeachingAssignmentNotFoundException extends NotFoundException {
    public TeachingAssignmentNotFoundException(UUID teachingAssignmentId) {
        super("Teaching assignment not found: " + teachingAssignmentId);
    }
}