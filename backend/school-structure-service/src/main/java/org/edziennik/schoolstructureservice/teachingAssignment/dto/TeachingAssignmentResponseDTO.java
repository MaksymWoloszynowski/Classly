package org.edziennik.schoolstructureservice.teachingAssignment.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeachingAssignmentResponseDTO {
    private UUID id;
    private UUID teacherId;
    private String teacherName;
    private UUID subjectId;
    private String subjectName;
    private UUID groupId;
    private String groupName;
}
