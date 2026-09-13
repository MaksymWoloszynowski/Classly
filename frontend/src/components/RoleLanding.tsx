import { Navigate } from "react-router-dom";
import useAuth from "../hooks/useAuth";

const RoleLanding = () => {
  const { auth } = useAuth();

  if (auth?.role === "ROLE_TEACHER") return <Navigate to="/teacher/home" replace />;
  if (auth?.role === "ROLE_STUDENT" || auth?.role === "ROLE_PARENT") {
    return <Navigate to="/student/home" replace />;
  }

  return <Navigate to="/login" replace />;
};

export default RoleLanding;
