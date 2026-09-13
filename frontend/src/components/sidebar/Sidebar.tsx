import { NavLink } from "react-router-dom";
import styles from "./Sidebar.module.css";
import useAuth from "../../hooks/useAuth";
import useStudentScope from "../../hooks/useStudentScope";

import {
  House,
  GraduationCap,
  CalendarDays,
  ClipboardCheck,
  BookOpenCheck,
  CalendarCheck,
  Menu,
  X,
  Mail,
  User,
} from "lucide-react";
import { useState } from "react";
import useLogout from "../../hooks/useLogout";

const studentMenu = [
  { to: "/student/dashboard", label: "Dashboard", icon: House },
  { to: "/student/grades", label: "Grades", icon: GraduationCap },
  { to: "/student/schedule", label: "Schedule", icon: CalendarDays },
  { to: "/student/assessments", label: "Assessments", icon: ClipboardCheck },
  { to: "/student/sessions", label: "Sessions", icon: BookOpenCheck },
  { to: "/student/attendance", label: "Attendance", icon: CalendarCheck },
];

const teacherMenu = [
  { to: "/teacher/dashboard", label: "Dashboard", icon: House },
  { to: "/teacher/grades", label: "Grades", icon: GraduationCap },
  { to: "/teacher/schedule", label: "My schedule", icon: CalendarDays },
  { to: "/teacher/realizations", label: "Realizations", icon: BookOpenCheck },
  { to: "/teacher/assessments", label: "Assessments", icon: ClipboardCheck },
];

const Sidebar = () => {
  const { auth } = useAuth();
  const logout = useLogout();
  const { activeStudent, availableStudents, selectStudent, isLoadingStudent } =
    useStudentScope();
  const isTeacher = auth?.role === "ROLE_TEACHER";
  const menu = isTeacher ? teacherMenu : studentMenu;
  const [menuOpen, setMenuOpen] = useState(false);
  const [logoutVisible, setLogoutVisible] = useState(false);

  return (
    <section className={styles.sidebar}>
      {menuOpen ? (
        <X
          className={styles.hamburger}
          onClick={() => setMenuOpen(!menuOpen)}
        />
      ) : (
        <Menu
          className={styles.hamburger}
          onClick={() => setMenuOpen(!menuOpen)}
        />
      )}

      {auth?.role === "ROLE_PARENT" && (
        <label className={styles.studentPicker}>
          <span>Profil ucznia</span>
          <select
            value={activeStudent?.id ?? ""}
            onChange={(event) => selectStudent(event.target.value)}
            disabled={isLoadingStudent || availableStudents.length === 0}
          >
            {availableStudents.map((student) => (
              <option key={student.id} value={student.id}>
                {student.firstName} {student.lastName}
              </option>
            ))}
          </select>
        </label>
      )}

      {isTeacher ? (
        <div className={styles.sidebarTitle}>
          <p>Teacher's panel</p>
          <p>
            {auth.teacher?.firstName} {auth.teacher?.lastName}
          </p>
        </div>
      ) : (
        <div className={styles.sidebarTitle}>
          <p>Student's panel</p>
          <p>
            {activeStudent?.firstName} {activeStudent?.lastName}
          </p>
        </div>
      )}

      <nav className={`${styles.nav} ${menuOpen ? styles.open : ""}`}>
        {menu.map(({ to, label, icon: Icon }) => (
          <NavLink
            key={to}
            to={to}
            className={({ isActive }) => (isActive ? styles.active : styles.link)}
            onClick={() => setMenuOpen(false)}
          >
            <Icon size={20} strokeWidth={1.8} />
            <span>{label}</span>
          </NavLink>
        ))}

        <NavLink
          to={"/messages"}
          className={({ isActive }) => (isActive ? styles.active : styles.link)}
          onClick={() => setMenuOpen(false)}
        >
          <Mail size={20} strokeWidth={1.8} />
          <span>Messages</span>
        </NavLink>
        <div
          className={styles.bottom}
          onClick={() => setLogoutVisible((prev) => !prev)}
        >
          <div className={styles.link}>
            <User size={20} strokeWidth={1.8} />
            <div>More</div>
            <span className={styles.arrow} />
          </div>
        </div>

        {logoutVisible && (
          <div className={styles.actionBox}>
              <NavLink className={styles.link} onClick={() => logout()} to={'/profile'}>Your profile</NavLink>
              <span></span>
              <p className={styles.logout} onClick={() => logout()}>Logout</p>
          </div>
        )}
      </nav>
    </section>
  );
};

export default Sidebar;
