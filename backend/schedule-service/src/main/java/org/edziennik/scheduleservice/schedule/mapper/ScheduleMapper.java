package org.edziennik.scheduleservice.schedule.mapper;

import org.edziennik.scheduleservice.additional_schedule.entity.AdditionalSchedule;
import org.edziennik.scheduleservice.schedule.dto.ScheduleOccurrenceDTO;
import org.edziennik.scheduleservice.schedule.dto.ScheduleRequestDTO;
import org.edziennik.scheduleservice.schedule.dto.ScheduleResponseDTO;
import org.edziennik.scheduleservice.schedule.entity.Schedule;
import org.edziennik.scheduleservice.schedule.entity.ScheduleOccurrence;

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
                .scheduleId(schedule.getId())
                .teachingAssignmentId(schedule.getTeachingAssignmentId())
                .date(occurrenceDate)
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .build();
    }

    public static ScheduleOccurrenceDTO occurrenceToDTO(ScheduleOccurrence occurrence) {
        return ScheduleOccurrenceDTO.builder()
                .date(occurrence.getDate())
                .dayOfWeek(occurrence.getDate().getDayOfWeek().getValue())
                .startTime(occurrence.getStartTime())
                .endTime(occurrence.getEndTime())
                .room(occurrence.getRoom())
                .build();
    }

    public static ScheduleOccurrenceDTO occurrenceToDTOFromAdditional(AdditionalSchedule schedule) {
        return ScheduleOccurrenceDTO.builder()
                .date(schedule.getDate())
                .dayOfWeek(schedule.getDate().getDayOfWeek().getValue())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .room(schedule.getRoom())
                .build();
    }
}
