package org.edziennik.teachingservice.session.mapper;

import org.edziennik.teachingservice.session.dto.SessionRequestDTO;
import org.edziennik.teachingservice.session.dto.SessionResponseDTO;
import org.edziennik.teachingservice.session.entity.Session;

public class SessionMapper {
    public static SessionResponseDTO toDTO(Session session) {
        return SessionResponseDTO.builder()
                .id(session.getId())
                .scheduleId(session.getScheduleId())
                .teachingAssignmentId(session.getTeachingAssignmentId())
                .description(session.getDescription())
                .date(session.getDate())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .build();
    }

    public static Session toModel(SessionRequestDTO dto) {
        return Session.builder()
                .scheduleId(dto.getScheduleId())
                .teachingAssignmentId(dto.getTeachingAssignmentId())
                .description(dto.getDescription())
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }
}
