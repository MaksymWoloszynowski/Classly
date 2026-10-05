package org.classly.schoolstructureservice.parent.mapper;

import org.classly.schoolstructureservice.parent.dto.AdminParentResponseDTO;
import org.classly.schoolstructureservice.parent.dto.ParentRequestDTO;
import org.classly.schoolstructureservice.parent.dto.ParentResponseDTO;
import org.classly.schoolstructureservice.parent.dto.ParentSummaryDTO;
import org.classly.schoolstructureservice.parent.entity.Parent;
import org.classly.schoolstructureservice.student.dto.StudentSummaryDTO;
import org.classly.schoolstructureservice.student.mapper.StudentMapper;

import java.util.Set;
import java.util.stream.Collectors;

public class ParentMapper {
    public static ParentResponseDTO toDTO(Parent parent) {
        return ParentResponseDTO.builder()
                .id(parent.getId())
                .firstName(parent.getFirstName())
                .lastName(parent.getLastName())
                .students(getStudents(parent))
                .build();
    }

    public static AdminParentResponseDTO toAdminDTO(Parent parent) {
        return AdminParentResponseDTO.builder()
                .id(parent.getId())
                .socialID(parent.getSocialId())
                .firstName(parent.getFirstName())
                .lastName(parent.getLastName())
                .students(getStudents(parent))
                .build();
    }

    public static Parent toModel(ParentRequestDTO parentRequestDTO) {
        return Parent.builder()
                .firstName(parentRequestDTO.getFirstName())
                .lastName(parentRequestDTO.getLastName())
                .socialId(parentRequestDTO.getSocialId())
                .build();
    }

    public static ParentSummaryDTO toSummaryDTO(Parent parent) {
        return ParentSummaryDTO.builder()
                .id(parent.getId())
                .firstName(parent.getFirstName())
                .lastName(parent.getLastName())
                .build();
    }

    private static Set<StudentSummaryDTO> getStudents(Parent parent) {
        return parent.getStudents().stream()
                .map(StudentMapper::toSummaryDTO)
                .collect(Collectors.toSet());
    }
}
