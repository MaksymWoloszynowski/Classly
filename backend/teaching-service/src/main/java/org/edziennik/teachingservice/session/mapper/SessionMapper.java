package org.edziennik.teachingservice.session.mapper;

import org.edziennik.teachingservice.session.dto.SessionRequestDTO;
import org.edziennik.teachingservice.session.dto.SessionResponseDTO;
import org.edziennik.teachingservice.session.entity.Session;

public class SessionMapper {
    public static SessionResponseDTO toDTO(Session session) {
        return SessionResponseDTO.builder()
                .id(session.getId())
                .teachingAssignmentId(session.getTeachingAssignmentId())
                .description(session.getDescription())
                .date(session.getDate())
                .build();
    }

    public static Session toModel(SessionRequestDTO dto) {
        return Session.builder()
                .teachingAssignmentId(dto.getTeachingAssignmentId())
                .description(dto.getDescription())
                .build();
    }
}
