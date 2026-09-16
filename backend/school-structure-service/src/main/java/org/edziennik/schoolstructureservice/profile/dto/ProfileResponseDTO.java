package org.edziennik.schoolstructureservice.profile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "User profile dependent on the role")
public class ProfileResponseDTO {
    @Schema(description = "User ID")
    private UUID userId;

    @Schema(description = "User role")
    private String role;

    @Schema(description = "Student date for students")
    private StudentResponseDTO student;

    @Schema(description = "Teacher date for teachers")
    private TeacherResponseDTO teacher;

    @Schema(description = "Parent date for parents")
    private ParentResponseDTO parent;
}
