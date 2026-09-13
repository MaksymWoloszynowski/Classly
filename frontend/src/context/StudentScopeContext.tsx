import { createContext, useEffect, useState } from "react";
import api from "../api/api";
import useAuth from "../hooks/useAuth";
import type { Student } from "../types/domain/student";
import type { StudentSummary } from "../types/domain/studentSummary";
import type { TeachingAssignment } from "../types/domain/teachingAssignment";

type StudentScopeContextValue = {
  activeStudent: Student | null;
  isLoadingStudent: boolean;
  selectStudent: (studentId: string) => void;
  availableStudents: StudentSummary[];
  teachingAssignments: TeachingAssignment[];
};

const StudentScopeContext = createContext<StudentScopeContextValue | null>(null);

export const StudentScopeProvider = ({ children }: { children: React.ReactNode }) => {
  const { auth } = useAuth();
  const [activeStudent, setActiveStudent] = useState<Student | null>(null);
  const [selectedStudentId, setSelectedStudentId] = useState<string | null>(null);
  const [isLoadingStudent, setIsLoadingStudent] = useState(false);
  const [teachingAssignments, setTeachingAssignmnets] = useState<TeachingAssignment[]>([]);

  const availableStudents = auth?.parent?.students ?? [];

  useEffect(() => {
    if (auth?.role === "ROLE_STUDENT") {
      setActiveStudent(auth.student);
      setSelectedStudentId(auth.student?.id ?? null);
      return;
    }

    if (auth?.role === "ROLE_PARENT") {
      setSelectedStudentId((currentId) =>
        availableStudents.some((student) => student.id === currentId)
          ? currentId
          : (availableStudents[0]?.id ?? null),
      );
      return;
    }

    setActiveStudent(null);
    setSelectedStudentId(null);
  }, [auth, availableStudents]);

  useEffect(() => {
    if (auth?.role !== "ROLE_PARENT" || !selectedStudentId) return;

    const loadStudent = async () => {
      setIsLoadingStudent(true);
      try {
        const studentResponse = await api.get<Student>(`/api/student/${selectedStudentId}`);
        setActiveStudent(studentResponse.data);
      } catch (error) {
        console.error("Error fetching selected student:", error);
        setActiveStudent(null);
      } finally {
        setIsLoadingStudent(false);
      }
    };

    loadStudent();
  }, [auth?.role, selectedStudentId]);

  useEffect(() => {
  if (!activeStudent?.groupId) return;

  const loadAssignments = async () => {
    try {
      const response = await api.get<TeachingAssignment[]>(
        `/api/teaching-assignment?groupId=${activeStudent.groupId}`
      );

      setTeachingAssignmnets(response.data);
    } catch (error) {
      console.error("Error fetching teaching assignments:", error);
      setTeachingAssignmnets([]);
    }
  };

  loadAssignments();
}, [activeStudent?.groupId]);

  return (
    <StudentScopeContext.Provider
      value={{
        activeStudent,
        isLoadingStudent,
        selectStudent: setSelectedStudentId,
        availableStudents,
        teachingAssignments,
      }}
    >
      {children}
    </StudentScopeContext.Provider>
  );
};

export default StudentScopeContext;
