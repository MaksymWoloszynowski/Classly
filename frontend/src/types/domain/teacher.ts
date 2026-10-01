import type { TeachingAssignment } from "./teachingAssignment";
import type { GroupSummary } from "./groupSummary";

interface BaseTeacher {
    id: string;
    firstName: string;
    lastName: string;
    teachingAssignments: TeachingAssignment[];
    homeroomGroups: GroupSummary[];
}

export type Teacher = BaseTeacher;

export type AdminTeacher = BaseTeacher & {
    socialId: string;
}