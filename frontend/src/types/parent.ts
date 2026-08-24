import type { StudentSummary } from "./studentSummary";

export type Parent = {
    id: string;
    firstName: string;
    lastName: string;
    students: Set<StudentSummary>;
}