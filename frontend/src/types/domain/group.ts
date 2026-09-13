import type { StudentSummary } from "./studentSummary";

export type Group = {
    id: string;
    name: string;
    students: StudentSummary[];
    homeroomTeacher: string;
}