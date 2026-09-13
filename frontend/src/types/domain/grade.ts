import type { GradeType } from "../enums";

export type Grade = {
    id: string;
    categoryId: string;
    grade: number;
    type: GradeType;
    description: string;
    weight: number;
    date: string;
    studentFullName: string;
    subjectName: string;
};
