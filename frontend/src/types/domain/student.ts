import type { ParentSummary } from "./parentSummary";

interface BaseStudent {
  id: string;
  firstName: string;
  lastName: string;
  dateOfBirth: Date;
  groupId: string;
  groupName: string;
  parents: ParentSummary[];
}

export type Student = BaseStudent;

export type AdminStudent = BaseStudent & {
  socialId: string;
};
