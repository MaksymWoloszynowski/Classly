import type { AssessmentType, AttendanceType } from "./enums";

export const assessmentTypeLabels: Record<AssessmentType, string> = {
  TEST: "Test",
  QUIZ: "Quiz",
  CLASS_TEST: "Class test",
  HOMEWORK: "Homework",
};

export const attendanceTypeLabels: Record<AttendanceType, string> = {
  PRESENT: "Present",
  TARDY: "Tardy",
  ABSENT: "Absent",
  UNEXCUSED_ABSENCE: "Unexcused Absence",
};