package org.classly.schoolstructureservice.student.mapper;

import org.classly.schoolstructureservice.parent.dto.ParentSummaryDTO;
import org.classly.schoolstructureservice.parent.mapper.ParentMapper;
import org.classly.schoolstructureservice.student.dto.AdminStudentResponseDTO;
import org.classly.schoolstructureservice.student.dto.StudentRequestDTO;
import org.classly.schoolstructureservice.student.dto.StudentResponseDTO;
import org.classly.schoolstructureservice.student.dto.StudentSummaryDTO;
import org.classly.schoolstructureservice.student.entity.Student;

import java.util.List;
import java.util.Set;
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
                .parents(getParents(student))
                .build();
    }

    public static AdminStudentResponseDTO toAdminDTO(Student student) {
        return AdminStudentResponseDTO.builder()
                .id(student.getId())
                .socialId(student.getSocialId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .dateOfBirth(student.getDateOfBirth())
                .groupId(student.getGroup() != null ? student.getGroup().getId() : null)
                .groupName(student.getGroup() != null ? student.getGroup().getName() : null)
                .parents(getParents(student))
                .build();
    }

    public static Student toModel(StudentRequestDTO studentRequestDTO) {
        return Student.builder()
                .firstName(studentRequestDTO.getFirstName())
                .lastName(studentRequestDTO.getLastName())
                .socialId(studentRequestDTO.getSocialId())
                .dateOfBirth(studentRequestDTO.getDateOfBirth())
                .build();
    }

    public static StudentSummaryDTO toSummaryDTO(Student student) {
        return StudentSummaryDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .build();
    }

    private static Set<ParentSummaryDTO> getParents(Student student) {
        return student.getParents().stream()
                .map(ParentMapper::toSummaryDTO)
                .collect(Collectors.toSet());
    }
}