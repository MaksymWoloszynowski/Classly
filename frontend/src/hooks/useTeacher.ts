import { useContext } from "react";
import TeacherContext from "../context/TeacherContext";

const useTeacher = () => {
  const context = useContext(TeacherContext);

  if (context === null) {
    throw new Error("useTeacher must be used within TeacherProvider");
  }

  return context;
};

export default useTeacher;
