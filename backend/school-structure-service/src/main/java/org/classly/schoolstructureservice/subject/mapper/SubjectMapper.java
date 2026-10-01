package org.classly.schoolstructureservice.subject.mapper;

import org.classly.schoolstructureservice.subject.dto.SubjectRequestDTO;
import org.classly.schoolstructureservice.subject.dto.SubjectResponseDTO;
import org.classly.schoolstructureservice.subject.entity.Subject;

public class SubjectMapper {
    public static SubjectResponseDTO toDTO(Subject subject) {
        return SubjectResponseDTO.builder()
                .id(subject.getId())
                .name(subject.getName())
                .build();
    }

    public static Subject toModel(SubjectRequestDTO subjectRequestDTO) {
        return Subject.builder()
                .name(subjectRequestDTO.getName())
                .build();
    }
}
