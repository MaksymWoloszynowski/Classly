package org.edziennik.teachingservice.attendance.dto;

import lombok.*;
import org.edziennik.teachingservice.attendance.entity.AttendanceType;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponseDTO {
    private UUID id;
    private UUID sessionId;
    private UUID studentId;
    private String studentFullName;
    private AttendanceType type;
}