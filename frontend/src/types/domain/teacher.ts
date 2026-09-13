import type { TeachingAssignment } from "./teachingAssignment";

export type Teacher = {
    id: string;
    firstName: string;
    lastName: string;
    teachingAssignments: TeachingAssignment[];
}
