import type { AttendanceType } from "../enums"

export type Attendance = {
    id: string,
    studentId: string,
    studentFullName: string,
    type: AttendanceType,
    subject: string,
    teacher: string,
    date: Date,
    startTime: string,
    endTime: string
}