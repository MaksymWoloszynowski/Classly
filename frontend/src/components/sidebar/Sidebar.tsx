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
import ErrorMessage from "../errorMessage/ErrorMessage";
import useLocalePath from "../../hooks/useLocalePath";
import { useTranslation } from "../../hooks/useTranslation";
import type { TranslationKey } from "../../i18n/translations";

const studentMenu = [
  { to: "/student/dashboard", label: "dashboard", icon: House },
  { to: "/student/grades", label: "grades", icon: GraduationCap },
  { to: "/student/schedule", label: "schedule", icon: CalendarDays },
  { to: "/student/assessments", label: "assessments", icon: ClipboardCheck },
  { to: "/student/sessions", label: "sessions", icon: BookOpenCheck },
  { to: "/student/attendance", label: "attendance", icon: CalendarCheck },
];

const teacherMenu = [
  { to: "/teacher/dashboard", label: "dashboard", icon: House },
  { to: "/teacher/grades", label: "grades", icon: GraduationCap },
  { to: "/teacher/schedule", label: "mySchedule", icon: CalendarDays },
  { to: "/teacher/realizations", label: "realizations", icon: BookOpenCheck },
  { to: "/teacher/assessments", label: "assessments", icon: ClipboardCheck },
];

const Sidebar = () => {
  const { auth } = useAuth();
  const logout = useLogout();
  const localePath = useLocalePath();
  const { t } = useTranslation();
  const {
    activeStudent,
    availableStudents,
    selectStudent,
    isLoadingStudent,
    error,
  } = useStudentScope();
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
          <span>{t("studentProfile")}</span>
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

      {error && <ErrorMessage message={error} />}

      {isTeacher ? (
        <div className={styles.sidebarTitle}>
          <p>{t("teacherPanel")}</p>
          <p>
            {auth.teacher?.firstName} {auth.teacher?.lastName}
          </p>
        </div>
      ) : (
        <div className={styles.sidebarTitle}>
          <p>{t("studentPanel")}</p>
          <p>
            {activeStudent?.firstName} {activeStudent?.lastName}
          </p>
        </div>
      )}

      <nav className={`${styles.nav} ${menuOpen ? styles.open : ""}`}>
        {menu.map(({ to, label, icon: Icon }) => (
          <NavLink
            key={to}
            to={localePath(to)}
            className={({ isActive }) =>
              isActive ? styles.active : styles.link
            }
            onClick={() => setMenuOpen(false)}
          >
            <Icon size={20} strokeWidth={1.8} />
            <span>{t(label as TranslationKey)}</span>
          </NavLink>
        ))}

        <NavLink
          to={localePath("/messages")}
          className={({ isActive }) => (isActive ? styles.active : styles.link)}
          onClick={() => setMenuOpen(false)}
        >
          <Mail size={20} strokeWidth={1.8} />
          <span>{t("messages")}</span>
        </NavLink>
        <div
          className={styles.bottom}
          onClick={() => setLogoutVisible((prev) => !prev)}
        >
          <div className={styles.link}>
            <User size={20} strokeWidth={1.8} />
            <div>{t("more")}</div>
            <span className={styles.arrow} />
          </div>
        </div>

        {logoutVisible && (
          <div className={styles.actionBox}>
            <NavLink
              className={styles.link}
              onClick={() => logout()}
              to={localePath("/profile")}
            >
              {t("yourProfile")}
            </NavLink>
            <span></span>
            <p className={styles.logout} onClick={() => logout()}>
              {t("logout")}
            </p>
          </div>
        )}
      </nav>
    </section>
  );
};

export default Sidebar;
