package org.edziennik.schoolstructureservice.teacher.mapper;

import org.edziennik.schoolstructureservice.group.dto.GroupSummaryDTO;
import org.edziennik.schoolstructureservice.group.mapper.GroupMapper;
import org.edziennik.schoolstructureservice.student.dto.StudentSummaryDTO;
import org.edziennik.schoolstructureservice.student.mapper.StudentMapper;
import org.edziennik.schoolstructureservice.teacher.dto.TeacherRequestDTO;
import org.edziennik.schoolstructureservice.teacher.dto.TeacherResponseDTO;
import org.edziennik.schoolstructureservice.teacher.entity.Teacher;
import org.edziennik.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentResponseDTO;
import org.edziennik.schoolstructureservice.teachingAssignment.mapper.TeachingAssignmentMapper;

import java.util.Comparator;
import java.util.stream.Collectors;

public class TeacherMapper {
    public static TeacherResponseDTO toDTO(Teacher teacher) {
        return TeacherResponseDTO.builder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .teachingAssignments(teacher.getTeachingAssignments().stream()
                        .map(TeachingAssignmentMapper::toDTO)
                        .sorted(Comparator.comparing(TeachingAssignmentResponseDTO::getGroupName))
                        .toList())
                .homeroomGroups(teacher.getGroups().stream()
                        .map(GroupMapper::toSummaryDTO)
                        .sorted(Comparator.comparing(GroupSummaryDTO::getName))
                        .toList())
                .build();
    }

    public static Teacher toModel(TeacherRequestDTO teacherRequestDTO) {
        return Teacher.builder()
                .firstName(teacherRequestDTO.getFirstName())
                .lastName(teacherRequestDTO.getLastName())
                .build();
    }
}
