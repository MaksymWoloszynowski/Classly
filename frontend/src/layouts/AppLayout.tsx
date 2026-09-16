import { Outlet } from "react-router-dom";
import Sidebar from "../components/sidebar/Sidebar";
import TopBar from "../components/topBar/TopBar";

import styles from "./AppLayout.module.css";

const AppLayout = () => {
  return (
    <div className={styles.layout}>
      <Sidebar />
      <div className={styles.mainArea}>
        <TopBar />
        <main className={styles.content}>
          <Outlet />
        </main>
      </div>
    </div>
  );
};

export default AppLayout;
