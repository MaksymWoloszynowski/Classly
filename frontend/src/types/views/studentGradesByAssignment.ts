import type { Student } from "../domain/student";
import type { TeachingAssignment } from "../domain/teachingAssignment";
import type { SubjectGrades } from "./subjectGrades";

type StudentId = Student["id"];
type TeachingAssignmentId = TeachingAssignment["id"];

export type StudentGradesByAssignment = Record<
  TeachingAssignmentId,
  Record<StudentId, SubjectGrades>
>;
export type GradesByStudent = Record<StudentId, SubjectGrades>;
