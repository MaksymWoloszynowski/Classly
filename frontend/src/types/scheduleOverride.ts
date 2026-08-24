export type ScheduleOverride = {
    id: string;
    scheduleId: string;
    date: Date;
    type: string;
    substituteTeacherId: string;
    substituteTeacherName: string;
    substituteSubjectId: string;
    substituteSubjectName: string;
    newRoom: string;
}