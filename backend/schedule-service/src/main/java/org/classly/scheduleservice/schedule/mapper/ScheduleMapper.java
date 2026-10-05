package org.classly.scheduleservice.schedule.mapper;

import org.classly.scheduleservice.additional_schedule.entity.AdditionalSchedule;
import org.classly.scheduleservice.schedule.dto.ScheduleOccurrenceDTO;
import org.classly.scheduleservice.schedule.dto.ScheduleRequestDTO;
import org.classly.scheduleservice.schedule.dto.ScheduleResponseDTO;
import org.classly.scheduleservice.schedule.entity.Schedule;
import org.classly.scheduleservice.schedule.entity.ScheduleOccurrence;
import org.classly.scheduleservice.schedule_override.entity.ScheduleOverride;

import java.time.LocalDate;

public class ScheduleMapper {
    public static ScheduleResponseDTO toDTO(Schedule schedule) {
        return ScheduleResponseDTO.builder()
                .id(schedule.getId())
                .teachingAssignmentId(schedule.getTeachingAssignmentId())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .validTo(schedule.getValidTo())
                .validFrom(schedule.getValidFrom())
                .room(schedule.getRoom())
                .dayOfWeek(schedule.getDayOfWeek())
                .build();
    }

    public static void updateModel(Schedule schedule, ScheduleRequestDTO dto) {
        schedule.setTeachingAssignmentId(dto.getTeachingAssignmentId());
        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setRoom(dto.getRoom());
        schedule.setValidFrom(dto.getValidFrom());
        schedule.setValidTo(dto.getValidTo());
    }

    public static Schedule toModel(ScheduleRequestDTO scheduleDTO) {
        return Schedule.builder()
                .teachingAssignmentId(scheduleDTO.getTeachingAssignmentId())
                .dayOfWeek(scheduleDTO.getDayOfWeek())
                .room(scheduleDTO.getRoom())
                .validFrom(scheduleDTO.getValidFrom())
                .validTo(scheduleDTO.getValidTo())
                .build();
    }

    public static ScheduleOccurrence toOccurrence(Schedule schedule, LocalDate date) {
        LocalDate occurrenceDate = date.plusDays(schedule.getDayOfWeek() - 1);

        return ScheduleOccurrence.builder()
                .schedule(schedule)
                .teachingAssignmentId(schedule.getTeachingAssignmentId())
                .date(occurrenceDate)
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .room(schedule.getRoom())
                .teachingAssignmentId(schedule.getTeachingAssignmentId())
                .build();
    }

    public static ScheduleOccurrenceDTO occurrenceToDTO(ScheduleOccurrence occurrence) {
        return ScheduleOccurrenceDTO.builder()
                .scheduleId(occurrence.getSchedule().getId())
                .date(occurrence.getDate())
                .startTime(occurrence.getStartTime())
                .endTime(occurrence.getEndTime())
                .room(occurrence.getRoom())
                .teachingAssignmentId(occurrence.getTeachingAssignmentId())
                .build();
    }

    public static ScheduleOccurrenceDTO occurrenceToDTOFromAdditional(AdditionalSchedule schedule) {
        return ScheduleOccurrenceDTO.builder()
                .scheduleId(schedule.getId())
                .date(schedule.getDate())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .room(schedule.getRoom())
                .teachingAssignmentId(schedule.getTeachingAssignmentId())
                .build();
    }

    public static ScheduleOccurrenceDTO occurrenceToDTOFromOverride(ScheduleOverride schedule) {
        return ScheduleOccurrenceDTO.builder()
                .scheduleId(schedule.getId())
                .date(schedule.getDate())
                .startTime(schedule.getSchedule().getStartTime())
                .endTime(schedule.getSchedule().getEndTime())
                .room(schedule.getSchedule().getRoom())
                .teachingAssignmentId(schedule.getSubstituteTeachingAssignmentId())
                .build();
    }
}
