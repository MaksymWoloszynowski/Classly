import { Link, useLocation } from "react-router-dom";
import {
  getCanonicalPath,
  getLocalizedPath,
  useLocale,
  type Locale,
} from "../../hooks/useLocalePath";
import useLocalePath from "../../hooks/useLocalePath";
import { useTranslation } from "../../hooks/useTranslation";
import styles from "./TopBar.module.css";
import ReactCountryFlag from "react-country-flag";

const TopBar = () => {
  const location = useLocation();
  const locale = useLocale();
  const localePath = useLocalePath();
  const { t } = useTranslation();
  const flagWidth = "1.75em";
  const flagHeight = "1.5em";

  const changeLocale = (nextLocale: Locale) => {
    if (nextLocale === locale) return;

    const pathWithoutLocale =
      location.pathname.replace(/^\/(pl|en)/, "") || "/";
    const canonicalPath = getCanonicalPath(pathWithoutLocale, locale);

    window.location.assign(getLocalizedPath(canonicalPath, nextLocale));
  };

  return (
    <header className={styles.topBar}>
      <Link
        className={styles.logo}
        to={localePath("/")}
        aria-label="Go to home page"
      >
        <span className={styles.logoMark}>C</span>
        <span>Classly</span>
      </Link>

      <div className={styles.languageSwitch} aria-label={t("language")}>
        <button
          type="button"
          className={styles.flagButton}
          onClick={() => changeLocale("pl")}
          aria-label={t("polish")}
          aria-pressed={locale === "pl"}
          title={t("polish")}
        >
          <ReactCountryFlag
            countryCode="PL"
            svg
            style={{ width: flagWidth, height: flagHeight }}
          />
        </button>
        <button
          type="button"
          className={styles.flagButton}
          onClick={() => changeLocale("en")}
          aria-label={t("english")}
          aria-pressed={locale === "en"}
          title={t("english")}
        >
          <ReactCountryFlag
            countryCode="GB"
            svg
            style={{ width: flagWidth, height: flagHeight }}
          />
        </button>
      </div>
    </header>
  );
};

export default TopBar;
