import { NavLink } from "react-router-dom";
import styles from "./Sidebar.module.css";

const Sidebar = () => {
  return (
    <section className={styles.sidebar}>
      <nav className={styles.nav}>
        <NavLink
          to="/home"
          className={({ isActive }) => (isActive ? styles.active : styles.link)}
        >
          <span>Home</span>
        </NavLink>

        <NavLink
          to="/grades"
          className={({ isActive }) => (isActive ? styles.active : styles.link)}
        >
          <span>Grades</span>
        </NavLink>

        <NavLink
          to="/schedule"
          className={({ isActive }) => (isActive ? styles.active : styles.link)}
        >
          <span>Schedule</span>
        </NavLink>

        <NavLink
          to="/assessments"
          className={({ isActive }) => (isActive ? styles.active : styles.link)}
        >
          <span>Assessments</span>
        </NavLink>

        <NavLink
          to="/sessions"
          className={({ isActive }) => (isActive ? styles.active : styles.link)}
        >
          <span>Sessions</span>
        </NavLink>

        <NavLink
          to="/attendance"
          className={({ isActive }) => (isActive ? styles.active : styles.link)}
        >
          <span>Attendance</span>
        </NavLink>
      </nav>
    </section>
  );
};

export default Sidebar;
