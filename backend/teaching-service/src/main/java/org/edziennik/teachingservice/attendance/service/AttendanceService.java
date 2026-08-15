package org.edziennik.teachingservice.attendance.service;

import org.edziennik.teachingservice.attendance.dto.AttendanceBatchRequestDTO;
import org.edziennik.teachingservice.attendance.dto.AttendanceResponseDTO;
import org.edziennik.teachingservice.attendance.entity.Attendance;
import org.edziennik.teachingservice.attendance.exception.AttendanceNotFoundException;
import org.edziennik.teachingservice.attendance.mapper.AttendanceMapper;
import org.edziennik.teachingservice.attendance.repository.AttendanceRepository;
import org.edziennik.teachingservice.grpc.SchoolStructureGrpcClient;
import org.edziennik.teachingservice.session.entity.Session;
import org.edziennik.teachingservice.session.exception.SessionNotFoundException;
import org.edziennik.teachingservice.session.repository.SessionRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final SessionRepository sessionRepository;
    private final SchoolStructureGrpcClient schoolStructureClient;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             SessionRepository sessionRepository,
                             SchoolStructureGrpcClient schoolStructureClient) {
        this.attendanceRepository = attendanceRepository;
        this.sessionRepository = sessionRepository;
        this.schoolStructureClient = schoolStructureClient;
    }

    public List<AttendanceResponseDTO> getAttendance(UUID sessionId, UUID studentId) {
        Specification<Attendance> spec = Specification.allOf();

        if (studentId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("studentId"), studentId));
        }
        if (sessionId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("sessionId"), sessionId));
        }
        return mapToDTOList(attendanceRepository.findAll(spec));
    }

    @Transactional
    public List<AttendanceResponseDTO> createAttendanceBatch(AttendanceBatchRequestDTO dto) {
        Session session = sessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new SessionNotFoundException("Session not found with ID: " + dto.getSessionId()));

        List<Attendance> entries = dto.getEntries().stream()
                .map(entry -> Attendance.builder()
                        .session(session)
                        .studentId(entry.getStudentId())
                        .type(entry.getType())
                        .build())
                .toList();

        List<Attendance> saved = attendanceRepository.saveAll(entries);
        return mapToDTOList(saved);
    }

    public void deleteAttendance(UUID id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found with ID: " + id));
        attendanceRepository.delete(attendance);
    }

    private List<AttendanceResponseDTO> mapToDTOList(List<Attendance> attendances) {
        Set<UUID> studentIds = attendances.stream().map(Attendance::getStudentId).collect(Collectors.toSet());
        Map<UUID, String> studentNames = schoolStructureClient.getStudentNames(studentIds);

        return attendances.stream().map(attendance -> {
            AttendanceResponseDTO responseDto = AttendanceMapper.toDTO(attendance);
            responseDto.setStudentFullName(studentNames.get(attendance.getStudentId()));
            return responseDto;
        }).toList();
    }
}