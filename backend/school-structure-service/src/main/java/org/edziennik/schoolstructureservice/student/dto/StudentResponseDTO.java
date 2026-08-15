package org.edziennik.schoolstructureservice.student.dto;

import lombok.*;
import org.edziennik.schoolstructureservice.parent.dto.ParentSummaryDTO;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private UUID groupId;
    private String groupName;
    private Set<ParentSummaryDTO> parents;
}
