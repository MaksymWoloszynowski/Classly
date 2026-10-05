import type { ScheduleOverrideType } from "..";

export type ScheduleOverride = {
    id: string;
    scheduleId: string;
    date: Date;
    type: ScheduleOverrideType;
    substituteTeacherId: string;
    substituteTeacherName: string;
    substituteSubjectId: string;
    substituteSubjectName: string;
    newRoom: string;
}