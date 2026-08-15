package org.edziennik.scheduleservice.schedule.util.schedule_override_strategy;

import org.edziennik.scheduleservice.grpc.SchoolStructureGrpcClient;
import org.edziennik.scheduleservice.schedule.dto.ScheduleOccurrenceDTO;
import org.edziennik.scheduleservice.schedule.entity.OccurrenceStatus;
import org.edziennik.scheduleservice.schedule_override.entity.ScheduleOverride;
import org.edziennik.schoolstructureservice.grpc.SubjectResponse;
import org.edziennik.schoolstructureservice.grpc.TeacherResponse;
import org.springframework.stereotype.Component;

@Component
public class ScheduleSubstitutionOverrideStrategy implements ScheduleOverrideStrategy {
    private final SchoolStructureGrpcClient schoolStructureClient;

    public ScheduleSubstitutionOverrideStrategy(SchoolStructureGrpcClient schoolStructureClient) {
        this.schoolStructureClient = schoolStructureClient;
    }

    @Override
    public void apply(ScheduleOccurrenceDTO dto, ScheduleOverride override) {
        if (override.getSubstituteTeacherId() != null) {
            TeacherResponse subTeacher = schoolStructureClient.getGrpcTeacher(override.getSubstituteTeacherId());
            dto.setTeacherName(subTeacher.getName());
        }
        if (override.getSubstituteSubjectId() != null) {
            SubjectResponse subSubject = schoolStructureClient.getGrpcSubject(override.getSubstituteSubjectId());
            dto.setSubjectName(subSubject.getName());
        }
        if (override.getNewRoom() != null) {
            dto.setRoom(override.getNewRoom());
        }
    }
}
