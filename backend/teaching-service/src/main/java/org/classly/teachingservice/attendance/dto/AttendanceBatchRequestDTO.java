package org.classly.teachingservice.attendance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Batch attendance data for a teaching session")
public class AttendanceBatchRequestDTO {
    @NotNull
    @Schema(description = "Session ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID sessionId;

    @NotEmpty
    @Valid
    @Schema(description = "Attendance entries for students in the session", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AttendanceEntryDTO> entries;
}