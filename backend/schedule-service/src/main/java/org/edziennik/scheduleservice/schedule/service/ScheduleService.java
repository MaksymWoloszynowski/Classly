package org.edziennik.scheduleservice.schedule.service;

import org.edziennik.scheduleservice.additional_schedule.entity.AdditionalSchedule;
import org.edziennik.scheduleservice.additional_schedule.repository.AdditionalScheduleRepository;
import org.edziennik.scheduleservice.grpc.SchoolStructureGrpcClient;
import org.edziennik.scheduleservice.schedule.dto.ScheduleOccurrenceDTO;
import org.edziennik.scheduleservice.schedule.dto.ScheduleRequestDTO;
import org.edziennik.scheduleservice.schedule.dto.ScheduleResponseDTO;
import org.edziennik.scheduleservice.schedule.entity.Schedule;
import org.edziennik.scheduleservice.schedule.entity.ScheduleOccurrence;
import org.edziennik.scheduleservice.schedule.exception.ScheduleNotFoundException;
import org.edziennik.scheduleservice.schedule.mapper.ScheduleMapper;
import org.edziennik.scheduleservice.schedule.repository.ScheduleRepository;
import org.edziennik.scheduleservice.schedule_override.entity.ScheduleOverride;
import org.edziennik.scheduleservice.schedule_override.mapper.ScheduleOverrideMapper;
import org.edziennik.scheduleservice.schedule_override.repository.ScheduleOverrideRepository;
import org.edziennik.schoolstructureservice.grpc.TeachingAssignmentResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ScheduleService {
    private final SchoolStructureGrpcClient schoolStructureClient;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleOverrideRepository scheduleOverrideRepository;
    private final AdditionalScheduleRepository additionalScheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           ScheduleOverrideRepository scheduleOverrideRepository,
                           AdditionalScheduleRepository additionalScheduleRepository,
                           SchoolStructureGrpcClient schoolStructureClient) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleOverrideRepository = scheduleOverrideRepository;
        this.schoolStructureClient = schoolStructureClient;
        this.additionalScheduleRepository = additionalScheduleRepository;
    }

    public List<ScheduleResponseDTO> getAllSchedules() {
        return mapToDTOList(scheduleRepository.findAll());
    }

    public ScheduleResponseDTO getScheduleById(UUID id) {
        return mapToDTO(getSchedule(id));
    }

    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO dto) {
        Schedule saved = scheduleRepository.save(ScheduleMapper.toModel(dto));
        return mapToDTO(saved);
    }

    public ScheduleResponseDTO updateSchedule(UUID id, ScheduleRequestDTO dto) {
        Schedule schedule = getSchedule(id);

        schedule.setTeachingAssignmentId(dto.getTeachingAssignmentId());
        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setRoom(dto.getRoom());
        schedule.setValidFrom(dto.getValidFrom());
        schedule.setValidTo(dto.getValidTo());

        Schedule updated = scheduleRepository.save(schedule);
        return mapToDTO(updated);
    }

    public void deleteSchedule(UUID id) {
        scheduleRepository.delete(getSchedule(id));
    }

    public List<ScheduleOccurrenceDTO> getGroupScheduleForDate(UUID groupId, LocalDate from, LocalDate to) {
        List<UUID> teachingAssignmentIds = schoolStructureClient.getTeachingAssignmentIdsByGroup(groupId);

        List<Schedule> schedules = scheduleRepository.findByTeachingAssignmentIdInAndValidFromLessThanEqualAndValidToGreaterThanEqual(
                teachingAssignmentIds, from, to);
        List<ScheduleOccurrence> occurrences = schedules.stream()
                .map(entry -> ScheduleMapper.toOccurrence(entry, from))
                .toList();

        List<UUID> scheduleIds = schedules.stream().map(Schedule::getId).toList();
        List<ScheduleOverride> overrides = scheduleOverrideRepository
                .findByScheduleIdInAndDateBetween(scheduleIds, from, to);

        List<AdditionalSchedule> additionalSessions = additionalScheduleRepository
                .findByTeachingAssignmentIdInAndDateBetween(teachingAssignmentIds, from, to);

        Map<UUID, TeachingAssignmentResponse> assignmentDataById = fetchAssignmentData(occurrences, additionalSessions);

        List<ScheduleOccurrenceDTO> result = new ArrayList<>(
                mergeOccurrencesWithOverrides(occurrences, overrides, assignmentDataById)
        );
        result.addAll(mapAdditionalSessions(additionalSessions, assignmentDataById));

        List<ScheduleOccurrenceDTO> filtered = result.stream().filter(entry -> !entry.getDate().isBefore(from) && !entry.getDate().isAfter(to)).toList();

        return filtered;
    }

    private Map<UUID, TeachingAssignmentResponse> fetchAssignmentData(
            List<ScheduleOccurrence> occurrences,
            List<AdditionalSchedule> additionalSessions) {

        Set<UUID> ids = Stream.concat(
                occurrences.stream().map(ScheduleOccurrence::getTeachingAssignmentId),
                additionalSessions.stream().map(AdditionalSchedule::getTeachingAssignmentId)
        ).collect(Collectors.toSet());

        return schoolStructureClient.getTeachingAssignments(ids);
    }

    private List<ScheduleOccurrenceDTO> mapAdditionalSessions(List<AdditionalSchedule> sessions, Map<UUID, TeachingAssignmentResponse> assignmentDataById) {

        return sessions.stream().map(session -> {
            TeachingAssignmentResponse assignment = assignmentDataById.get(session.getTeachingAssignmentId());

            ScheduleOccurrenceDTO dto = ScheduleMapper.occurrenceToDTOFromAdditional(session);
            dto.setSubjectName(assignment.getSubject());
            dto.setTeacherName(assignment.getTeacher());

            return dto;
        }).toList();
    }

    private ScheduleResponseDTO mapToDTO(Schedule schedule) {
        TeachingAssignmentResponse assignment = schoolStructureClient.getGrpcTeachingAssignment(schedule.getTeachingAssignmentId());
        return buildScheduleResponseDTO(schedule, assignment);
    }

    private List<ScheduleResponseDTO> mapToDTOList(List<Schedule> schedules) {
        Set<UUID> ids = schedules.stream().map(Schedule::getTeachingAssignmentId).collect(Collectors.toSet());
        Map<UUID, TeachingAssignmentResponse> assignmentDataById = schoolStructureClient.getTeachingAssignments(ids);

        return schedules.stream()
                .map(entry -> buildScheduleResponseDTO(entry, assignmentDataById.get(entry.getTeachingAssignmentId())))
                .toList();
    }

    private ScheduleResponseDTO buildScheduleResponseDTO(Schedule schedule, TeachingAssignmentResponse assignment) {
        ScheduleResponseDTO dto = ScheduleMapper.toDTO(schedule);
        dto.setSubjectName(assignment.getSubject());
        dto.setTeacherName(assignment.getTeacher());
        dto.setGroupName(assignment.getGroup());
        return dto;
    }

    private Schedule getSchedule(UUID id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found with ID: " + id));
    }

    private record OverrideKey(UUID scheduleId, LocalDate date) {
    }

    private List<ScheduleOccurrenceDTO> mergeOccurrencesWithOverrides(
            List<ScheduleOccurrence> occurrences,
            List<ScheduleOverride> overrides,
            Map<UUID, TeachingAssignmentResponse> assignmentDataById) {

        Map<OverrideKey, ScheduleOverride> overrideByKey = overrides.stream()
                .collect(Collectors.toMap(
                        o -> new OverrideKey(o.getSchedule().getId(), o.getDate()),
                        o -> o
                ));

        List<ScheduleOccurrenceDTO> result = new ArrayList<>();

        for (ScheduleOccurrence occurrence : occurrences) {
            OverrideKey key = new OverrideKey(occurrence.getScheduleId(), occurrence.getDate());
            ScheduleOverride override = overrideByKey.get(key);

            TeachingAssignmentResponse assignment = assignmentDataById.get(occurrence.getTeachingAssignmentId());

            ScheduleOccurrenceDTO dto = ScheduleMapper.occurrenceToDTO(occurrence);
            dto.setSubjectName(assignment.getSubject());
            dto.setTeacherName(assignment.getTeacher());
            dto.setOverride(override != null ? ScheduleOverrideMapper.toDTO(override) : null);

            result.add(dto);
        }

        return result;
    }
}