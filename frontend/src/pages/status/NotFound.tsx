import { Link } from "react-router-dom";
import styles from "./StatusPage.module.css";
import useLocalePath from "../../hooks/useLocalePath";
import { useTranslation } from "../../hooks/useTranslation";
import useAuth from "@hooks/useAuth";

const NotFound = () => {
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
      <section className={styles.content} aria-labelledby="not-found-title">
        <div className={styles.code}>404</div>
        <div className={styles.title} id="not-found-title">
          {t("notFound")}
        </div>
        <p className={styles.description}>
          {t("notFoundDescription")}
        </p>
        <div className={styles.actions}>
          <Link className={styles.primary} to={goToDashboard()}>
            {t("goToDashboard")}
          </Link>
        </div>
      </section>
    </main>
  );
};

export default NotFound;
