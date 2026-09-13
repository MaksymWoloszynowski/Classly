import type { Parent } from "./domain/parent";
import type { Student } from "./domain/student";
import type { Teacher } from "./domain/teacher";

export type StudentProfile = {
  userId: string;
  role: "ROLE_STUDENT";
  student: Student;
  parent: null;
  teacher: null;
};

export type ParentProfile = {
  userId: string;
  role: "ROLE_PARENT";
  student: null;
  parent: Parent;
  teacher: null;
};

export type TeacherProfile = {
  userId: string;
  role: "ROLE_TEACHER";
  student: null;
  parent: null;
  teacher: Teacher;
};

export type Profile =
  | StudentProfile
  | ParentProfile
  | TeacherProfile;