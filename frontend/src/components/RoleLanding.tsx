import { Navigate } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import useLocalePath from "../hooks/useLocalePath";
import LandingPage from "../pages/landing/LandingPage";

const RoleLanding = () => {
  const { auth } = useAuth();
  const localePath = useLocalePath();

  if (auth?.role === "ROLE_TEACHER")
    return <Navigate to={localePath("/teacher/dashboard")} replace />;
  if (auth?.role === "ROLE_STUDENT" || auth?.role === "ROLE_PARENT") {
    return <Navigate to={localePath("/student/dashboard")} replace />;
  }
  if (auth?.role === "ROLE_ADMIN") {
    return <Navigate to={localePath("/admin/dashboard")} replace />;
  }

  return <LandingPage />;
};

export default RoleLanding;
