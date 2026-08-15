import { Routes, Route } from "react-router-dom";
import Grades from "./pages/grades/Grades";
import AppLayout from "./layouts/AppLayout";

function App() {
  return (
    <Routes>

    <Route element={<AppLayout />}>
      <Route path="/home" element={<h1>Home</h1>} />
      <Route path="/grades" element={<Grades />} />
      <Route path="/attendance" element={<h1>Attendance</h1>} />
      <Route path="/schedule" element={<h1>Schedule</h1>} />
      <Route path="/assessments" element={<h1>Assessments</h1>} />
      <Route path="/sessions" element={<h1>Sessions</h1>} />
      </Route>
    </Routes>
  );
}

export default App;
