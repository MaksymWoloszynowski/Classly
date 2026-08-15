package org.edziennik.teachingservice.attendance.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.edziennik.teachingservice.attendance.entity.AttendanceType;

import java.util.UUID;

@Getter
@Setter
public class AttendanceEntryDTO {
    @NotNull
    private UUID studentId;

    @NotNull
    private AttendanceType type;
}