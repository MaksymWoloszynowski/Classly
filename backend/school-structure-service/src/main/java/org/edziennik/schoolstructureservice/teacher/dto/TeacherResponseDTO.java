package org.edziennik.schoolstructureservice.teacher.dto;

import lombok.*;
import org.edziennik.schoolstructureservice.group.dto.GroupResponseDTO;
import org.edziennik.schoolstructureservice.group.dto.GroupSummaryDTO;
import org.edziennik.schoolstructureservice.teachingAssignment.dto.TeachingAssignmentResponseDTO;

import java.util.List;
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
    private List<TeachingAssignmentResponseDTO> teachingAssignments;
    private List<GroupSummaryDTO> homeroomGroups;
}
