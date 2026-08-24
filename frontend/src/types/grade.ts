export type GradeType =  "CURRENT" | "QUIZ" | "HOMEWORK" | "TEST" | "CLASS_TEST" | "PARTICIPATION"


export type Grade = {
    id: string;
    grade: number;
    type: GradeType;
    description: string;
    classificationPeriod: string;
    weight: number;
    date: string;
    studentId: string;
    studentFullName: string;
    subjectId: string;
    subjectName: string;
};
