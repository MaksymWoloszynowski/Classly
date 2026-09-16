error id: file://<WORKSPACE>/backend/schedule-service/src/main/java/org/edziennik/scheduleservice/schedule/dto/ScheduleOccurrenceDTO.java:_empty_/Getter#
file://<WORKSPACE>/backend/schedule-service/src/main/java/org/edziennik/scheduleservice/schedule/dto/ScheduleOccurrenceDTO.java
empty definition using pc, found symbol in pc: _empty_/Getter#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 241
uri: file://<WORKSPACE>/backend/schedule-service/src/main/java/org/edziennik/scheduleservice/schedule/dto/ScheduleOccurrenceDTO.java
text:
```scala
package org.edziennik.scheduleservice.schedule.dto;

import lombok.*;
import org.edziennik.scheduleservice.schedule_override.dto.ScheduleOverrideResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@G@@etter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleOccurrenceDTO {
    private UUID scheduleId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private UUID teachingAssignmentId;
    private String subjectName;
    private String teacherName;
    private String groupName;
    private String room;
    private ScheduleOverrideResponseDTO override;
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/Getter#