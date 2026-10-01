package org.classly.scheduleservice.schedule.service;

import lombok.RequiredArgsConstructor;
import org.classly.scheduleservice.additional_schedule.entity.AdditionalSchedule;
import org.classly.scheduleservice.additional_schedule.repository.AdditionalScheduleRepository;
import org.classly.scheduleservice.grpc.SchoolStructureGrpcClient;
import org.classly.scheduleservice.schedule.dto.ScheduleOccurrenceDTO;
import org.classly.scheduleservice.schedule.entity.Schedule;
import org.classly.scheduleservice.schedule.entity.ScheduleOccurrence;
import org.classly.scheduleservice.schedule.exception.TeachingAssignmentNotFoundException;
import org.classly.scheduleservice.schedule.mapper.ScheduleMapper;
import org.classly.scheduleservice.schedule.repository.ScheduleRepository;
import org.classly.scheduleservice.schedule.util.ScheduleOccurrenceGenerator;
import org.classly.scheduleservice.schedule_override.dto.ScheduleOverrideResponseDTO;
import org.classly.scheduleservice.schedule_override.entity.ScheduleOverride;
import org.classly.scheduleservice.schedule_override.mapper.ScheduleOverrideMapper;
import org.classly.scheduleservice.schedule_override.repository.ScheduleOverrideRepository;
import org.classly.schoolstructureservice.grpc.TeachingAssignmentResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleQueryService {
    private final SchoolStructureGrpcClient schoolStructureClient;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleOverrideRepository scheduleOverrideRepository;
    private final AdditionalScheduleRepository additionalScheduleRepository;
    private final ScheduleOccurrenceGenerator scheduleOccurrenceGenerator;

    public List<ScheduleOccurrenceDTO> getScheduleForGroup(UUID groupId, LocalDate from, LocalDate to) {
        List<UUID> assignmentIds = schoolStructureClient.getTeachingAssignmentIdsByGroup(groupId);

        return buildSchedule(assignmentIds, from, to, List.of());
    }

    public List<ScheduleOccurrenceDTO> getScheduleForTeacher(UUID teacherId, LocalDate from, LocalDate to) {
        List<UUID> assignmentIds = schoolStructureClient.getTeachingAssignmentIdsByTeacher(teacherId);
        List<ScheduleOverride> teacherOverrides = scheduleOverrideRepository.findBySubstituteTeachingAssignmentIdInAndDateBetween(assignmentIds, from, to);

        return buildSchedule(assignmentIds, from, to, teacherOverrides);
    }


    private List<ScheduleOccurrenceDTO> buildSchedule(List<UUID> assignmentIds, LocalDate from, LocalDate to, List<ScheduleOverride> teacherOverrides) {
        List<Schedule> schedules = scheduleRepository.findOverlapping(assignmentIds, from, to);
        List<ScheduleOccurrence> occurrences = scheduleOccurrenceGenerator.generate(schedules, from, to);
        List<ScheduleOverride> overrides = scheduleOverrideRepository.findByScheduleIdInAndDateBetween(getScheduleIds(schedules), from, to);
        List<AdditionalSchedule> additionalSessions = additionalScheduleRepository.findByTeachingAssignmentIdInAndDateBetween(assignmentIds, from, to);

        Set<UUID> allAssignmentIds = collectTeachingAssignmentIds(occurrences, additionalSessions, teacherOverrides);
        Map<UUID, TeachingAssignmentResponse> assignmentsById = schoolStructureClient.getTeachingAssignments(allAssignmentIds);

        List<ScheduleOccurrenceDTO> result = new ArrayList<>(mergeOccurrencesWithOverrides(occurrences, overrides, assignmentsById));
        result.addAll(mapAdditionalSessions(additionalSessions, assignmentsById));
        result.addAll(mapOverrideSessions(teacherOverrides, assignmentsById));

        return result.stream()
                .sorted(Comparator.comparing(ScheduleOccurrenceDTO::getDate)
                        .thenComparing(ScheduleOccurrenceDTO::getStartTime))
                .toList();
    }

    private List<ScheduleOccurrenceDTO> mergeOccurrencesWithOverrides(List<ScheduleOccurrence> occurrences, List<ScheduleOverride> overrides, Map<UUID, TeachingAssignmentResponse> assignmentDataById) {
        Map<OverrideKey, ScheduleOverride> overrideByKey = overrides.stream()
                .collect(Collectors.toMap(
                        o -> new OverrideKey(o.getSchedule().getId(), o.getDate()),
                        o -> o
                ));

        List<ScheduleOccurrenceDTO> result = new ArrayList<>();

        for (ScheduleOccurrence occurrence : occurrences) {
            OverrideKey key = new OverrideKey(occurrence.getSchedule().getId(), occurrence.getDate());
            ScheduleOverride override = overrideByKey.get(key);

            UUID teachingAssignmentId = occurrence.getTeachingAssignmentId();
            ScheduleOccurrenceDTO dto = ScheduleMapper.occurrenceToDTO(occurrence);

            enrichScheduleOccurrenceDTO(assignmentDataById, teachingAssignmentId, dto);
            dto.setOverride(override != null ? mapOverrideToDTO(override, assignmentDataById) : null);

            result.add(dto);
        }

        return result;
    }

    private List<ScheduleOccurrenceDTO> mapAdditionalSessions(List<AdditionalSchedule> sessions, Map<UUID, TeachingAssignmentResponse> assignmentDataById) {
        return sessions.stream().map(session -> {
            UUID teachingAssignmentId = session.getTeachingAssignmentId();
            ScheduleOccurrenceDTO dto = ScheduleMapper.occurrenceToDTOFromAdditional(session);

            enrichScheduleOccurrenceDTO(assignmentDataById, teachingAssignmentId, dto);
            return dto;
        }).toList();
    }

    private List<ScheduleOccurrenceDTO> mapOverrideSessions(List<ScheduleOverride> sessions, Map<UUID, TeachingAssignmentResponse> assignmentDataById) {
        return sessions.stream().map(session -> {
            UUID teachingAssignmentId = session.getSubstituteTeachingAssignmentId();
            ScheduleOccurrenceDTO dto = ScheduleMapper.occurrenceToDTOFromOverride(session);

            enrichScheduleOccurrenceDTO(assignmentDataById, teachingAssignmentId, dto);
            return dto;
        }).toList();
    }

    private ScheduleOverrideResponseDTO mapOverrideToDTO(ScheduleOverride override, Map<UUID, TeachingAssignmentResponse> assignmentDataById) {
        UUID teachingAssignmentId = override.getSchedule().getTeachingAssignmentId();
        TeachingAssignmentResponse assignment = requireAssignment(assignmentDataById, teachingAssignmentId);

        ScheduleOverrideResponseDTO dto = ScheduleOverrideMapper.toDTO(override);
        dto.setSubstituteSubjectName(assignment.getSubject());
        dto.setSubstituteTeacherName(assignment.getTeacher());

        return dto;
    }

    private TeachingAssignmentResponse requireAssignment(Map<UUID, TeachingAssignmentResponse> assignmentDataById, UUID teachingAssignmentId) {
        TeachingAssignmentResponse assignment = assignmentDataById.get(teachingAssignmentId);

        if (assignment == null) {
            throw new TeachingAssignmentNotFoundException(teachingAssignmentId);
        }

        return assignment;
    }

    private void enrichScheduleOccurrenceDTO(Map<UUID, TeachingAssignmentResponse> assignmentDataById, UUID teachingAssignmentId, ScheduleOccurrenceDTO dto) {
        TeachingAssignmentResponse assignment = requireAssignment(assignmentDataById, teachingAssignmentId);

        dto.setSubjectName(assignment.getSubject());
        dto.setTeacherName(assignment.getTeacher());
        dto.setGroupName(assignment.getGroup());
    }

    private Set<UUID> collectTeachingAssignmentIds(List<ScheduleOccurrence> occurrences, List<AdditionalSchedule> additionalSessions, List<ScheduleOverride> overrides) {
        Set<UUID> ids = new HashSet<>();

        occurrences.stream()
                .map(ScheduleOccurrence::getTeachingAssignmentId)
                .forEach(ids::add);

        additionalSessions.stream()
                .map(AdditionalSchedule::getTeachingAssignmentId)
                .forEach(ids::add);

        overrides.stream()
                .map(override -> override.getSchedule().getTeachingAssignmentId())
                .forEach(ids::add);

        return ids;
    }

    private record OverrideKey(UUID scheduleId, LocalDate date) {}

    private List<UUID> getScheduleIds(List<Schedule> schedules) {
        return schedules.stream()
                .map(Schedule::getId)
                .toList();
    }
}