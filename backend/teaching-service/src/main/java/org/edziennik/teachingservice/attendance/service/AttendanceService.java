package org.edziennik.teachingservice.attendance.service;

import org.edziennik.schoolstructureservice.grpc.StudentResponse;
import org.edziennik.schoolstructureservice.grpc.TeachingAssignmentResponse;
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
import org.edziennik.security.AuthenticatedUser;
import org.edziennik.teachingservice.security.TeacherAccessService;

import java.time.LocalDate;
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
    private final TeacherAccessService teacherAccessService;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             SessionRepository sessionRepository,
                             SchoolStructureGrpcClient schoolStructureClient, TeacherAccessService teacherAccessService) {
        this.attendanceRepository = attendanceRepository;
        this.sessionRepository = sessionRepository;
        this.schoolStructureClient = schoolStructureClient;
        this.teacherAccessService = teacherAccessService;
    }

    public List<AttendanceResponseDTO> getAttendance(UUID sessionId, UUID studentId) {
        Specification<Attendance> spec = Specification.allOf();

        if (studentId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("studentId"), studentId));
        }
        if (sessionId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("session").get("id"), sessionId));
        }
        return mapToDTOList(attendanceRepository.findAll(spec));
    }

    public List<AttendanceResponseDTO> getAttendanceByStudentAndDate(UUID studentId, LocalDate from, LocalDate to) {
        List<UUID> sessionIds = sessionRepository.findByDateBetween(from, to).stream().map(Session::getId).toList();

        List<Attendance> attendances = attendanceRepository.findBySessionIdInAndStudentId(sessionIds, studentId);

        return mapToDTOList(attendances);
    }

    @Transactional
    public List<AttendanceResponseDTO> createAttendanceBatch(AttendanceBatchRequestDTO dto, AuthenticatedUser user) {
        Session session = sessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new SessionNotFoundException("Session not found with ID: " + dto.getSessionId()));
        teacherAccessService.requireAssignmentAccess(user, session.getTeachingAssignmentId());

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

    public void deleteAttendance(UUID id, AuthenticatedUser user) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found with ID: " + id));
        attendanceRepository.delete(attendance);
    }

    private List<AttendanceResponseDTO> mapToDTOList(List<Attendance> attendances) {
        Set<UUID> studentIds = attendances.stream().map(Attendance::getStudentId).collect(Collectors.toSet());
        Set<UUID> groupIds = studentIds.stream().map(id -> UUID.fromString(schoolStructureClient.getStudent(id).getGroupId())).collect(Collectors.toSet());
        Set<UUID> teachingAssignmentsIds = groupIds.stream().map(schoolStructureClient::getTeachingAssignmentIdsByGroup).flatMap(List::stream).collect(Collectors.toSet());

        Map<UUID, StudentResponse> students = schoolStructureClient.getStudents(studentIds);
        Map<UUID, TeachingAssignmentResponse> teachingAssignmentResponseMap = schoolStructureClient.getTeachingAssignments(teachingAssignmentsIds);

        return attendances.stream().map(attendance -> {
            AttendanceResponseDTO responseDto = AttendanceMapper.toDTO(attendance);
            responseDto.setStudentFullName(students.get(attendance.getStudentId()).getFirstName() + " " + students.get(attendance.getStudentId()).getLastName());
            responseDto.setTeacher(teachingAssignmentResponseMap.get(attendance.getSession().getTeachingAssignmentId()).getTeacher());
            responseDto.setSubject(teachingAssignmentResponseMap.get(attendance.getSession().getTeachingAssignmentId()).getSubject());
            return responseDto;
        }).toList();
    }
}
