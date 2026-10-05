import { useContext } from "react";
import StudentScopeContext from "../context/StudentScopeContext";

const useStudentScope = () => {
  const context = useContext(StudentScopeContext);

  if (context === null) {
    throw new Error("useStudentScope must be used within StudentScopeProvider");
  }

  return context;
};

export default useStudentScope;
