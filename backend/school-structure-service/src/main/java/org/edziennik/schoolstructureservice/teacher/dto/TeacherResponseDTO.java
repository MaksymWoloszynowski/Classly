package org.edziennik.schoolstructureservice.teacher.dto;

import lombok.*;
import org.edziennik.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentResponseDTO;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private Set<TeachingAssignmentResponseDTO> teachingAssignments;
}
