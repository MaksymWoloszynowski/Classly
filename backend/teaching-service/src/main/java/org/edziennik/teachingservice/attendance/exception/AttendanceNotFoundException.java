package org.edziennik.teachingservice.attendance.exception;

import org.edziennik.teachingservice.exception.NotFoundException;

public class AttendanceNotFoundException extends NotFoundException {
    public AttendanceNotFoundException(String message) {
        super(message);
    }
}