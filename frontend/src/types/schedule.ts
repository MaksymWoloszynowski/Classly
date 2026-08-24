import type { ScheduleOverride } from "./scheduleOverride";

export type Schedule = {
    date: Date;
    dayOfWeek: number;
    startTime: string;
    endTime: string;
    subjectName: string;
    teacherName: string;
    room: string;
    override: ScheduleOverride | null;
}