package org.classly.teachingservice.attendance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.classly.teachingservice.attendance.entity.AttendanceType;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "Attendance status for one student")
public class AttendanceEntryDTO {
    @NotNull
    @Schema(description = "Student ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID studentId;

    @NotNull
    @Schema(description = "Attendance type", requiredMode = Schema.RequiredMode.REQUIRED)
    private AttendanceType type;
}