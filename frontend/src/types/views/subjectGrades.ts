import type { Grade } from "../domain/grade";

export type SubjectGrades = {
    grades: Grade[];
    average: number;
}