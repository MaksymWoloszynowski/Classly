package org.edziennik.schoolstructureservice.parent.dto;

import lombok.*;
import org.edziennik.schoolstructureservice.student.dto.StudentSummaryDTO;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParentResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private Set<StudentSummaryDTO> students;
}
