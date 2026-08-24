import { Routes, Route } from "react-router-dom";
import Grades from "./pages/grades/Grades";
import AppLayout from "./layouts/AppLayout";
import Login from "./pages/login/Login";
import Register from "./pages/register/Register";
import RequireAuth from "./components/RequireAuth";
import PersistLogin from "./pages/PersistLogin";
import Schedule from "./pages/schedule/Schedule";
import Sessions from "./pages/sessions/Sessions";
import Assessments from "./pages/assessments/Assessments";

function App() {
  return (
    <Routes>
      <Route path="login" element={<Login />} />
      <Route path="register" element={<Register />} />

      <Route element={<PersistLogin />}>
        <Route element={<RequireAuth allowedRoles={["ROLE_STUDENT"]} />}>
          <Route element={<AppLayout />}>
            <Route path="/home" element={<h1>Home</h1>} />
            <Route path="/grades" element={<Grades />} />
            <Route path="/attendance" element={<h1>Attendance</h1>} />
            <Route path="/schedule" element={<Schedule />} />
            <Route path="/assessments" element={<Assessments />} />
            <Route path="/sessions" element={<Sessions />} />
          </Route>
        </Route>
      </Route>
    </Routes>
  );
}

export default App;
