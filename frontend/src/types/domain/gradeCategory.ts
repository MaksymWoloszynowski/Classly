import type { GradeType } from "../enums";

export type GradeSummary = {
  id: string;
  grade: number;
  date: string;
  studentId: string;
  studentFullName?: string;
};

export type GradeCategory = {
  id: string;
  description: string;
  weight: number;
  type: GradeType;
  grades: GradeSummary[];
};
