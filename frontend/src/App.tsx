import { lazy, Suspense } from "react";
import { Navigate, Routes, Route } from "react-router-dom";
import AppLayout from "./layouts/AppLayout";
import RequireAuth from "./components/RequireAuth";
import PersistLogin from "./pages/PersistLogin";
import RoleLanding from "./components/RoleLanding";
import LocaleRoute from "./components/LocaleRoute";
import LoadingOverlay from "./components/loadingOverlay/LoadingOverlay";
import { I18nProvider } from "./i18n/I18nContext";

const Login = lazy(() => import("./pages/login/Login"));
const Register = lazy(() => import("./pages/register/Register"));
const SchedulePage = lazy(() => import("./pages/schedule/Schedule"));
const Sessions = lazy(() => import("./pages/sessions/Sessions"));
const AttendancePage = lazy(() => import("./pages/attendance/Attendance"));
const Realizations = lazy(() => import("./pages/realizations/Realizations"));
const TeacherAssessments = lazy(
  () => import("./pages/assessments/teacherAssessments/TeacherAssessments"),
);
const StudentAssessments = lazy(
  () => import("./pages/assessments/studentAssessment/StudentAssessments"),
);
const TeacherDashboard = lazy(
  () => import("./pages/dashboard/teacherDashboard/TeacherDashboard"),
);
const TeacherGrades = lazy(
  () => import("./pages/grades/teacherGrades/TeacherGrades"),
);
const StudentGrades = lazy(
  () => import("./pages/grades/studentGrades/StudentGrades"),
);
const StudentDashboard = lazy(
  () => import("./pages/dashboard/studentDashboard/StudentDashboard"),
);
const AdminDashboard = lazy(
  () => import("./pages/dashboard/adminDashboard/AdminDashboard"),
);
const AdminStudents = lazy(
  () => import("./pages/admin/adminStudents/AdminStudents"),
);
const AdminTeachers = lazy(
  () => import("./pages/admin/adminTeachers/AdminTeachers"),
);
const AdminParents = lazy(
  () => import("./pages/admin/adminParents/AdminParents"),
);
const AdminGroups = lazy(() => import("./pages/admin/adminGroups/AdminGroups"));
const AdminSubjects = lazy(
  () => import("./pages/admin/adminSubjects/AdminSubjects"),
);
const AdminTeachingAssignments = lazy(
  () =>
    import("./pages/admin/adminTeachingAssignments/AdminTeachingAssignments"),
);
const AdminClassificationPeriods = lazy(
  () =>
    import("./pages/admin/adminClassificationPeriods/AdminClassificationPeriods"),
);
const AdminSchedule = lazy(
  () =>
    import("./pages/admin/adminSchedule/AdminSchedule"),
);
const AdminAccessCodes = lazy(
  () =>
    import("./pages/admin/adminAccessCodes/AdminAccessCodes"),
);

const NotFound = lazy(() => import("./pages/status/NotFound"));
const Unauthorized = lazy(() => import("./pages/status/Unauthorized"));

function App() {
  return (
    <Suspense fallback={<LoadingOverlay />}>
      <Routes>
        <Route
          path="/"
          element={
            <I18nProvider locale="en">
              <RoleLanding />
            </I18nProvider>
          }
        />
        <Route path=":lang" element={<LocaleRoute />}>
          <Route path="login" element={<Login />} />
          <Route path="logowanie" element={<Login />} />
          <Route path="register" element={<Register />} />
          <Route path="rejestracja" element={<Register />} />
          <Route path="unauthorized" element={<Unauthorized />} />
          <Route path="brak-uprawnien" element={<Unauthorized />} />
          <Route path="not-found" element={<NotFound />} />
          <Route path="nie-znaleziono" element={<NotFound />} />

          <Route element={<PersistLogin />}>
            <Route index element={<RoleLanding />} />

            <Route
              element={
                <RequireAuth allowedRoles={["ROLE_STUDENT", "ROLE_PARENT"]} />
              }
            >
              <Route element={<AppLayout />}>
                <Route
                  path="student/dashboard"
                  element={<StudentDashboard />}
                />
                <Route path="uczen/panel" element={<StudentDashboard />} />
                <Route path="student/grades" element={<StudentGrades />} />
                <Route path="uczen/oceny" element={<StudentGrades />} />
                <Route path="student/attendance" element={<AttendancePage />} />
                <Route path="uczen/frekwencja" element={<AttendancePage />} />
                <Route path="student/schedule" element={<SchedulePage />} />
                <Route path="uczen/plan" element={<SchedulePage />} />
                <Route
                  path="student/assessments"
                  element={<StudentAssessments />}
                />
                <Route
                  path="uczen/sprawdziany"
                  element={<StudentAssessments />}
                />
                <Route path="student/sessions" element={<Sessions />} />
                <Route path="uczen/zajecia" element={<Sessions />} />
              </Route>
            </Route>

            <Route element={<RequireAuth allowedRoles={["ROLE_TEACHER"]} />}>
              <Route element={<AppLayout />}>
                <Route
                  path="teacher/dashboard"
                  element={<TeacherDashboard />}
                />
                <Route path="nauczyciel/panel" element={<TeacherDashboard />} />
                <Route path="teacher/grades" element={<TeacherGrades />} />
                <Route path="nauczyciel/oceny" element={<TeacherGrades />} />
                <Route path="teacher/schedule" element={<SchedulePage />} />
                <Route path="nauczyciel/plan" element={<SchedulePage />} />
                <Route path="teacher/realizations" element={<Realizations />} />
                <Route
                  path="nauczyciel/realizacje"
                  element={<Realizations />}
                />
                <Route
                  path="teacher/assessments"
                  element={<TeacherAssessments />}
                />
                <Route
                  path="nauczyciel/sprawdziany"
                  element={<TeacherAssessments />}
                />
              </Route>
            </Route>

            <Route element={<RequireAuth allowedRoles={["ROLE_ADMIN"]} />}>
              <Route element={<AppLayout />}>
                <Route path="admin/dashboard" element={<AdminDashboard />} />
                <Route path="admin/panel" element={<AdminDashboard />} />

                <Route path="admin/students" element={<AdminStudents />} />
                <Route path="admin/uczniowie" element={<AdminStudents />} />
                <Route path="admin/teachers" element={<AdminTeachers />} />
                <Route path="admin/nauczyciele" element={<AdminTeachers />} />
                <Route path="admin/parents" element={<AdminParents />} />
                <Route path="admin/rodzice" element={<AdminParents />} />
                <Route path="admin/groups" element={<AdminGroups />} />
                <Route path="admin/grupy" element={<AdminGroups />} />
                <Route path="admin/subjects" element={<AdminSubjects />} />
                <Route path="admin/przedmioty" element={<AdminSubjects />} />
                <Route
                  path="admin/teaching-assignments"
                  element={<AdminTeachingAssignments />}
                />
                <Route
                  path="admin/przypisania-nauczycieli"
                  element={<AdminTeachingAssignments />}
                />

                <Route
                  path="admin/okresy-klasyfikacyjne"
                  element={<AdminClassificationPeriods />}
                />
                <Route
                  path="admin/classification-periods"
                  element={<AdminClassificationPeriods />}
                />

                <Route path="admin/schedule" element={<AdminSchedule />} />
                <Route path="admin/plan" element={<AdminSchedule />} />
                <Route path="admin/access-codes" element={<AdminAccessCodes />} />
                <Route path="admin/kody-dostepu" element={<AdminAccessCodes />} />

              </Route>
            </Route>

            <Route path="home" element={<RoleLanding />} />
            <Route path="panel" element={<RoleLanding />} />
            <Route path="wiadomosci" element={<NotFound />} />
            <Route path="profil" element={<NotFound />} />
            <Route path="*" element={<NotFound />} />
          </Route>
        </Route>
        <Route path="*" element={<NavigateToDefaultLocale />} />
      </Routes>
    </Suspense>
  );
}

const NavigateToDefaultLocale = () => {
  const browserLanguage = navigator.language.toLowerCase().startsWith("pl")
    ? "pl"
    : "en";

  return <Navigate to={`/${browserLanguage}/login`} replace />;
};

export default App;
