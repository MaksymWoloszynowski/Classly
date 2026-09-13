import { Routes, Route } from "react-router-dom";
import AppLayout from "./layouts/AppLayout";
import Login from "./pages/login/Login";
import Register from "./pages/register/Register";
import RequireAuth from "./components/RequireAuth";
import PersistLogin from "./pages/PersistLogin";
import SchedulePage from "./pages/schedule/Schedule";
import Sessions from "./pages/sessions/Sessions";
import AttendancePage from "./pages/attendance/Attendance";
import Dashboard from "@pages/dashboard/Dashboard";
import RoleLanding from "./components/RoleLanding";
import Realizations from "./pages/realizations/Realizations";
import TeacherAssessments from "@pages/assessments/teacherAssessments/TeacherAssessments";
import StudentAssessments from "./pages/assessments/studentAssessment/StudentAssessments";
import TeacherDashboard from "@pages/dashboard/TeacherDashboard";
import TeacherGrades from "@pages/grades/teacherGrades/TeacherGrades";
import StudentGrades from "@pages/grades/studentGrades/StudentGrades";

function App() {
  return (
    <Routes>
      <Route path="login" element={<Login />} />
      <Route path="register" element={<Register />} />

      <Route element={<PersistLogin />}>
        <Route path="/" element={<RoleLanding />} />

        <Route element={<RequireAuth allowedRoles={["ROLE_STUDENT", "ROLE_PARENT"]} />}>
          <Route element={<AppLayout />}>
            <Route path="/student/home" element={<Dashboard />} />
            <Route path="/student/grades" element={<StudentGrades />} />
            <Route path="/student/attendance" element={<AttendancePage />} />
            <Route path="/student/schedule" element={<SchedulePage />} />
            <Route path="/student/assessments" element={<StudentAssessments />} />
            <Route path="/student/sessions" element={<Sessions />} />
          </Route>
        </Route>

        <Route element={<RequireAuth allowedRoles={["ROLE_TEACHER"]} />}>
          <Route element={<AppLayout />}>
            <Route path="/teacher/home" element={<TeacherDashboard />} />
            <Route path="/teacher/grades" element={<TeacherGrades />} />
            <Route path="/teacher/schedule" element={<SchedulePage />} />
            <Route path="/teacher/realizations" element={<Realizations />} />
            <Route path="/teacher/assessments" element={<TeacherAssessments />} />
          </Route>
        </Route>

        <Route path="/home" element={<RoleLanding />} />
        <Route path="*" element={<RoleLanding />} />
      </Route>
    </Routes>
  );
}

export default App;
