import { Link } from "react-router-dom";
import useLocalePath from "../../hooks/useLocalePath";
import styles from "./LandingPage.module.css";
import TopBar from "@components/topBar/TopBar";
import { useTranslation } from "@hooks/useTranslation";

const LandingPage = () => {
  const localePath = useLocalePath();
  const { t } = useTranslation();

  return (
    <main className={styles.page}>
      <TopBar />
      <section className={styles.hero}>
        <div className={styles.heroCopy}>
          <div className={styles.title}>{t("landingTitle")}</div>
          <p className={styles.description}>{t("landingDescription")}</p>
          <div className={styles.actions}>
            <Link className={styles.primary} to={localePath("/login")}>
              {t("signIn")}
            </Link>
            <Link className={styles.secondary} to={localePath("/register")}>
              {t("register")}
            </Link>
          </div>
        </div>

        <div className={styles.preview} aria-label="Classly overview">
          <div className={styles.previewTop}>
            <span>{t("landingToday")}</span>
          </div>
          <div className={styles.previewTitle}>{t("landingOverview")}</div>
          <div className={styles.previewGrid}>
            <div className={styles.previewCard}>
              <span>{t("schedule")}</span>
              <strong>08:00</strong>
              <small>{t("landingMathematics")}</small>
            </div>
            <div className={styles.previewCard}>
              <span>{t("assessments")}</span>
              <strong>3</strong>
              <small>{t("landingComingWeek")}</small>
            </div>
            <div className={`${styles.previewCard} ${styles.wideCard}`}>
              <span>{t("attendance")}</span>
              <div className={styles.progress}>
                <i />
              </div>
              <small>{t("landingClear")}</small>
            </div>
          </div>
        </div>
      </section>
    </main>
  );
};

export default LandingPage;
