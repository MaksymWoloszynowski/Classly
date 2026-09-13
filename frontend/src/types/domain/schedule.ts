import type { ScheduleOverride } from "./scheduleOverride";

export type Schedule = {
    scheduleId?: string;
    date: string;
    startTime: string;
    endTime: string;
    teachingAssignmentId: string;
    subjectName: string;
    teacherName: string;
    room: string;
    groupName: string;
    override: ScheduleOverride | null;
}
