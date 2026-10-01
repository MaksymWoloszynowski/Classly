import { Link } from "react-router-dom";
import styles from "./StatusPage.module.css";
import useLocalePath from "../../hooks/useLocalePath";
import { useTranslation } from "../../hooks/useTranslation";
import useAuth from "@hooks/useAuth";

const Unauthorized = () => {
  const localePath = useLocalePath();
  const {auth} = useAuth();
  const { t } = useTranslation();

  const goToDashboard = () => {
    switch (auth?.role) {
      case "ROLE_TEACHER":
        return localePath("/teacher/dashboard");
      case "ROLE_STUDENT":
      case "ROLE_PARENT":
        return localePath("/student/dashboard");
      case "ROLE_ADMIN":
        return localePath("/admin/dashboard");
      default:
        return localePath("/");
    }
  }

  return (
    <main className={styles.page}>
      <section className={styles.content} aria-labelledby="unauthorized-title">
        <div className={styles.code}>403</div>
        <div className={styles.title} id="unauthorized-title">
          {t("unauthorized")}
        </div>
        <p className={styles.description}>{t("unauthorizedDescription")}</p>
        <div className={styles.actions}>
          <Link className={styles.primary} to={goToDashboard()}>
            {t("goToDashboard")}
          </Link>
        </div>
      </section>
    </main>
  );
};

export default Unauthorized;
