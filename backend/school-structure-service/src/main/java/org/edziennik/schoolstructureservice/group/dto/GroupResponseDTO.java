package org.edziennik.schoolstructureservice.group.dto;

import lombok.*;
import org.edziennik.schoolstructureservice.student.dto.StudentSummaryDTO;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseDTO {
    private UUID id;
    private String name;
    private Set<StudentSummaryDTO> students;
}
