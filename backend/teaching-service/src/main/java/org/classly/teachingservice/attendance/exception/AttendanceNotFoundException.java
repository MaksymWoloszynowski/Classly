package org.classly.teachingservice.attendance.exception;

import org.classly.teachingservice.exception.NotFoundException;

public class AttendanceNotFoundException extends NotFoundException {
    public AttendanceNotFoundException(String message) {
        super(message);
    }
}