import type { StudentSummary } from "./studentSummary";
import type { TeachingAssignment } from "./teachingAssignment";

export type Group = {
    id: string;
    name: string;
    students: StudentSummary[];
    teachingAssignments: TeachingAssignment[];
    homeroomTeacher: string;
}