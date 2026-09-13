import type { AssessmentType } from "../enums";

export type Assessment = {
    id: string;
    teachingAssignmentId: string;
    subjectName: string;
    teacherName: string;
    dateMade: Date;
    dateDue: Date;
    type: AssessmentType;
    description: string;
}