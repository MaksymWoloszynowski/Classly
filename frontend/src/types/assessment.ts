export type AssessmentType = "TEST" | "QUIZ" | "CLASS_TEST" | "HOMEWORK"


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