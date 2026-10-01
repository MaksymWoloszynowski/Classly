import type { StudentSummary } from "./studentSummary";

interface BaseParent {
    id: string;
    firstName: string;
    lastName: string;
    students: StudentSummary[];
}

export type Parent = BaseParent

export type AdminParent = BaseParent & {
    socialId: string;
}