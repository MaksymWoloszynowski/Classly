package org.classly.schoolstructureservice.teachingAssignment.mapper;

import org.classly.schoolstructureservice.group.entity.Group;
import org.classly.schoolstructureservice.subject.entity.Subject;
import org.classly.schoolstructureservice.teacher.entity.Teacher;
import org.classly.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentResponseDTO;
import org.classly.schoolstructureservice.teachingAssignment.entity.TeachingAssignment;

public class TeachingAssignmentMapper {
    public static TeachingAssignmentResponseDTO toDTO(TeachingAssignment teachingAssignment) {
        return TeachingAssignmentResponseDTO.builder()
                .id(teachingAssignment.getId())
                .teacherId(teachingAssignment.getTeacher().getId())
                .teacherName(teachingAssignment.getTeacher().getFirstName() + " " + teachingAssignment.getTeacher().getLastName())
                .subjectId(teachingAssignment.getSubject().getId())
                .subjectName(teachingAssignment.getSubject().getName())
                .groupId(teachingAssignment.getGroup().getId())
                .groupName(teachingAssignment.getGroup().getName())
                .build();
    }

    public static TeachingAssignment toModel(Teacher teacher, Subject subject, Group group) {
        return TeachingAssignment.builder()
                .teacher(teacher)
                .subject(subject)
                .group(group)
                .build();
    }
}
