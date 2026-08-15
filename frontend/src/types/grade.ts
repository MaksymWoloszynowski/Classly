export type GradeType = "exam" | "homework" | "project" | "quiz" | "annual";

export type Grade = {
    id: string;
    grade: number;
    type: GradeType;
    description: string;
    semester: number;
    weight: number;
    date: string;
    studentId: string;
    studentFullName: string;
    subjectId: string;
    subjectName: string;
};
