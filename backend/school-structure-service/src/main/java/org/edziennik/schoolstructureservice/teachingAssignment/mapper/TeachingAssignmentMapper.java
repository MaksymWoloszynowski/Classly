package org.edziennik.schoolstructureservice.teachingAssignment.mapper;

import org.edziennik.schoolstructureservice.group.entity.Group;
import org.edziennik.schoolstructureservice.subject.entity.Subject;
import org.edziennik.schoolstructureservice.teacher.entity.Teacher;
import org.edziennik.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentResponseDTO;
import org.edziennik.schoolstructureservice.teachingAssignment.entity.TeachingAssignment;

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
