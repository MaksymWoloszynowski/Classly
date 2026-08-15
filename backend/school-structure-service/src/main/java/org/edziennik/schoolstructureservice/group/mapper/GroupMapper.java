package org.edziennik.schoolstructureservice.group.mapper;

import org.edziennik.schoolstructureservice.group.dto.GroupRequestDTO;
import org.edziennik.schoolstructureservice.group.dto.GroupResponseDTO;
import org.edziennik.schoolstructureservice.group.entity.Group;
import org.edziennik.schoolstructureservice.student.dto.StudentSummaryDTO;

import java.util.stream.Collectors;

public class GroupMapper {
    public static GroupResponseDTO toDTO(Group group) {
        return GroupResponseDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .students(group.getStudents().stream()
                        .map(s -> StudentSummaryDTO.builder()
                                .id(s.getId())
                                .firstName(s.getFirstName())
                                .lastName(s.getLastName())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Group toModel(GroupRequestDTO groupRequestDTO) {
        return Group.builder()
                .name(groupRequestDTO.getGroupName())
                .build();
    }
}