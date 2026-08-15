package org.edziennik.schoolstructureservice.teachingassignment.mapper;

import org.edziennik.schoolstructureservice.group.entity.Group;
import org.edziennik.schoolstructureservice.subject.entity.Subject;
import org.edziennik.schoolstructureservice.teacher.entity.Teacher;
import org.edziennik.schoolstructureservice.teachingassignment.dto.TeachingAssignmentResponseDTO;
import org.edziennik.schoolstructureservice.teachingassignment.entity.TeachingAssignment;

public class TeachingAssignmentMapper {
    public static TeachingAssignmentResponseDTO toDTO(TeachingAssignment teachingAssignment) {
        return TeachingAssignmentResponseDTO.builder().
                id(teachingAssignment.getId()).
                teacherFirstName(teachingAssignment.getTeacher().getFirstName()).
                teacherLastName(teachingAssignment.getTeacher().getLastName()).
                subjectName(teachingAssignment.getSubject().getName()).
                groupName(teachingAssignment.getGroup().getName())
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
