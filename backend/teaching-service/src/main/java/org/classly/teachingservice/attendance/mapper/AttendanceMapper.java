package org.classly.teachingservice.attendance.mapper;

import org.classly.teachingservice.attendance.dto.AttendanceResponseDTO;
import org.classly.teachingservice.attendance.entity.Attendance;

public class AttendanceMapper {
    public static AttendanceResponseDTO toDTO(Attendance attendance) {
        return AttendanceResponseDTO.builder()
                .id(attendance.getId())
                .studentId(attendance.getStudentId())
                .type(attendance.getType())
                .date(attendance.getSession().getDate())
                .startTime(attendance.getSession().getStartTime())
                .endTime(attendance.getSession().getEndTime())
                .build();
    }
}