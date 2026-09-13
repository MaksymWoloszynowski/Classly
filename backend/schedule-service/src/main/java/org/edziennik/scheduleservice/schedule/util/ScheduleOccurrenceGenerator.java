package org.edziennik.scheduleservice.schedule.util;

import org.edziennik.scheduleservice.schedule.entity.Schedule;
import org.edziennik.scheduleservice.schedule.entity.ScheduleOccurrence;
import org.edziennik.scheduleservice.schedule.mapper.ScheduleMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleOccurrenceGenerator {

    public List<ScheduleOccurrence> generate(List<Schedule> schedules, LocalDate from, LocalDate to) {
        long days = ChronoUnit.DAYS.between(from, to) + 1;

        LocalDate closestMonday = from.minusDays(from.getDayOfWeek().getValue() - 1);

        List<ScheduleOccurrence> occurrences = new ArrayList<>();

        for (int i = 0; i < (days / 7) + 2; i++) {
            int weekOffset = i * 7;

            occurrences.addAll(schedules.stream()
                    .map(entry ->
                            ScheduleMapper.toOccurrence(
                                    entry,
                                    closestMonday.plusDays(weekOffset)
                            )
                    )
                    .filter(occurrence -> {
                        LocalDate date = occurrence.getDate();
                        Schedule schedule = occurrence.getSchedule();

                        return !date.isBefore(schedule.getValidFrom())
                                && !date.isAfter(schedule.getValidTo())
                                && !date.isBefore(from)
                                && !date.isAfter(to);
                    })
                    .toList()
            );
        }

        return occurrences;
    }
}
