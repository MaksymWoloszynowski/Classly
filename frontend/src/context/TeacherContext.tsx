import { createContext } from "react";
import useAuth from "../hooks/useAuth";
import type { TeachingAssignment } from "../types/domain/teachingAssignment";

type TeacherContextValue = {
    isTeacher: boolean;
  teachingAssignments: TeachingAssignment[];
  isAssignmentTeacher: (id: string) => boolean;
};

const TeacherContext = createContext<TeacherContextValue | null>(null);

export const TeacherProvider = ({
  children,
}: {
  children: React.ReactNode;
}) => {
  const { auth } = useAuth();

  const isTeacher = auth?.role === "ROLE_TEACHER"

  const teachingAssignments = auth?.teacher?.teachingAssignments ?? [];

  const isAssignmentTeacher = (id: string) => {
    return isTeacher && teachingAssignments.some((assignment) => assignment.id === id);
  };

  return (
    <TeacherContext.Provider
      value={{
        isTeacher,
        teachingAssignments,
        isAssignmentTeacher,
      }}
    >
      {children}
    </TeacherContext.Provider>
  );
};

export default TeacherContext;
