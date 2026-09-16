import { Link } from "react-router-dom";
import styles from "./StatusPage.module.css";
import useLocalePath from "../../hooks/useLocalePath";
import { useTranslation } from "../../hooks/useTranslation";

const NotFound = () => {
  const localePath = useLocalePath();
  const { t } = useTranslation();

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
          <Link className={styles.primary} to={localePath("/")}>
            {t("goToDashboard")}
          </Link>
        </div>
      </section>
    </main>
  );
};

export default NotFound;
