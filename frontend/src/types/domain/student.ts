import type { ParentSummary } from "./parentSummary";

export type Student = {
    id: string;
    firstName: string;
    lastName: string;
    dateOfBirth: Date;
    groupId: string;
    groupName: string;
    parents: ParentSummary[];
}
