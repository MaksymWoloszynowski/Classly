package org.edziennik.schoolstructureservice.parent.mapper;

import org.edziennik.schoolstructureservice.parent.dto.ParentRequestDTO;
import org.edziennik.schoolstructureservice.parent.dto.ParentResponseDTO;
import org.edziennik.schoolstructureservice.parent.dto.ParentSummaryDTO;
import org.edziennik.schoolstructureservice.parent.entity.Parent;

public class ParentMapper {
    public static ParentResponseDTO toDTO(Parent parent) {
        return ParentResponseDTO.builder()
                .id(parent.getId())
                .firstName(parent.getFirstName())
                .lastName(parent.getLastName())
                .build();
    }

    public static Parent toModel(ParentRequestDTO parentRequestDTO) {
        return Parent.builder()
                .firstName(parentRequestDTO.getFirstName())
                .lastName(parentRequestDTO.getLastName())
                .build();
    }

    public static ParentSummaryDTO toSummaryDTO(Parent parent) {
        return ParentSummaryDTO.builder()
                .id(parent.getId())
                .firstName(parent.getFirstName())
                .lastName(parent.getLastName())
                .build();
    }
}
