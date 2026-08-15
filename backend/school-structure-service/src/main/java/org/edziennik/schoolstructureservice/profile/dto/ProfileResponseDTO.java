package org.edziennik.schoolstructureservice.profile.dto;

import lombok.*;
import org.edziennik.schoolstructureservice.parent.dto.ParentResponseDTO;
import org.edziennik.schoolstructureservice.student.dto.StudentResponseDTO;
import org.edziennik.schoolstructureservice.teacher.dto.TeacherResponseDTO;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDTO {
    private UUID userId;
    private StudentResponseDTO student;
    private TeacherResponseDTO teacher;
    private ParentResponseDTO parent;
}
