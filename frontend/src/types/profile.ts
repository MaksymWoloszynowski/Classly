import type { Parent } from "./parent";
import type { Student } from "./student";
import type { Teacher } from "./teacher";

export type Profile = {
    userId: string;
    role: string;
    student: Student | null;
    parent: Parent | null;
    teacher: Teacher | null;
}