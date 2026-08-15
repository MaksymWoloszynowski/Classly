package org.edziennik.schoolstructureservice.student.mapper;

import org.edziennik.schoolstructureservice.parent.mapper.ParentMapper;
import org.edziennik.schoolstructureservice.student.dto.StudentRequestDTO;
import org.edziennik.schoolstructureservice.student.dto.StudentResponseDTO;
import org.edziennik.schoolstructureservice.student.entity.Student;

import java.util.stream.Collectors;

public class StudentMapper {
    public static StudentResponseDTO toDTO(Student student) {
        return StudentResponseDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .dateOfBirth(student.getDateOfBirth())
                .groupId(student.getGroup() != null ? student.getGroup().getId() : null)
                .groupName(student.getGroup() != null ? student.getGroup().getName() : null)
                .parents(student.getParents().stream()
                        .map(ParentMapper::toSummaryDTO)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Student toModel(StudentRequestDTO studentRequestDTO) {
        return Student.builder()
                .firstName(studentRequestDTO.getFirstName())
                .lastName(studentRequestDTO.getLastName())
                .dateOfBirth(studentRequestDTO.getDateOfBirth())
                .build();
    }
}