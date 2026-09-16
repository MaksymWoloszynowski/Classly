export const assessmentTypes = [
  "TEST",
  "QUIZ",
  "CLASS_TEST",
  "HOMEWORK",
] as const;

export type AssessmentType = (typeof assessmentTypes)[number];

export const attendanceTypes = [
  "TARDY",
  "ABSENT",
  "UNEXCUSED_ABSENCE",
  "PRESENT",
] as const;

export type AttendanceType = (typeof attendanceTypes)[number];

export const gradeTypes = [
  "CURRENT",
  "QUIZ",
  "HOMEWORK",
  "TEST",
  "CLASS_TEST",
  "PARTICIPATION",
] as const;

export type GradeType = (typeof gradeTypes)[number]

export type SemesterGradeType =
  | "PROPOSED_SEMESTER"
  | "FINAL_SEMESTER"
  | "PROPOSED_ANNUAL"
  | "FINAL_ANNUAL";

export type ScheduleOverrideType = "CANCELLED" | "SUBSTITUTION" | "ROOM_CHANGE";
