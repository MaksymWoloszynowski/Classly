import { Link } from "react-router-dom";
import styles from "./StatusPage.module.css";
import useLocalePath from "../../hooks/useLocalePath";
import { useTranslation } from "../../hooks/useTranslation";

const Unauthorized = () => {
  const localePath = useLocalePath();
  const { t } = useTranslation();

  return (
    <main className={styles.page}>
      <section className={styles.content} aria-labelledby="unauthorized-title">
        <div className={styles.code}>403</div>
        <div className={styles.title} id="unauthorized-title">
          {t("unauthorized")}
        </div>
        <p className={styles.description}>{t("unauthorizedDescription")}</p>
        <div className={styles.actions}>
          <Link className={styles.primary} to={localePath("/")}>
            {t("goToDashboard")}
          </Link>
          <Link className={styles.secondary} to={localePath("/login")}>
            {t("changeAccount")}
          </Link>
        </div>
      </section>
    </main>
  );
};

export default Unauthorized;
