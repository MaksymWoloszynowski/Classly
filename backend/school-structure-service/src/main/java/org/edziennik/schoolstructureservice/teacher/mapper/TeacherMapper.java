package org.edziennik.schoolstructureservice.teacher.mapper;

import org.edziennik.schoolstructureservice.teacher.dto.TeacherRequestDTO;
import org.edziennik.schoolstructureservice.teacher.dto.TeacherResponseDTO;
import org.edziennik.schoolstructureservice.teacher.entity.Teacher;
import org.edziennik.schoolstructureservice.teachingAssignment.mapper.TeachingAssignmentMapper;

import java.util.stream.Collectors;

public class TeacherMapper {
    public static TeacherResponseDTO toDTO(Teacher teacher) {
        return TeacherResponseDTO.builder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .teachingAssignments(teacher.getTeachingAssignments().stream().map(TeachingAssignmentMapper::toDTO).collect(Collectors.toSet()))
                .build();
    }

    public static Teacher toModel(TeacherRequestDTO teacherRequestDTO) {
        return Teacher.builder()
                .firstName(teacherRequestDTO.getFirstName())
                .lastName(teacherRequestDTO.getLastName())
                .build();
    }
}
