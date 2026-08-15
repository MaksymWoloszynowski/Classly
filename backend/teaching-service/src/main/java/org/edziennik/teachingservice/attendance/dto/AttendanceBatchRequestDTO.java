package org.edziennik.teachingservice.attendance.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AttendanceBatchRequestDTO {
    @NotNull
    private UUID sessionId;

    @NotEmpty
    @Valid
    private List<AttendanceEntryDTO> entries;
}