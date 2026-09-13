import { Link } from "react-router-dom";
import useAuth from "../../hooks/useAuth";

const TeacherDashboard = () => {
  const { auth } = useAuth();
  const assignments = auth?.teacher?.teachingAssignments ?? [];

  return (
    <section>
      <h1>Dzień dobry, {auth?.teacher?.firstName}</h1>
      <p>Twoje prowadzone klasy: {assignments.length}.</p>
      <Link to="/teacher/schedule">Zobacz swój plan lekcji</Link>
    </section>
  );
};

export default TeacherDashboard;
