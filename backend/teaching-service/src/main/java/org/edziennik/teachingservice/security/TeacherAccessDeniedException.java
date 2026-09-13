package org.edziennik.teachingservice.security;

public class TeacherAccessDeniedException extends RuntimeException {
    public TeacherAccessDeniedException() {
        super("Only the teacher assigned to this subject may manage its realization and attendance");
    }
}
