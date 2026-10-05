import type { SemesterGradeType } from "../enums";

export type SemesterGrade = {
    id: string;
    grade: number;
    type: SemesterGradeType;
    classificationPeriod: string;
    studentId: string;
    teachingAssignmentId: string;
}