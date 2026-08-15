package org.edziennik.schoolstructureservice.teachingassignment.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeachingAssignmentResponseDTO {
    private UUID id;
    private String teacherFirstName;
    private String teacherLastName;
    private String subjectName;
    private String groupName;
}
