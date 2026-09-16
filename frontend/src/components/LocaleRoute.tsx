import { Navigate, Outlet, useParams } from "react-router-dom";
import { useEffect } from "react";
import { I18nProvider } from "../i18n/I18nContext";
import type { Locale } from "../hooks/useLocalePath";

const LocaleRoute = () => {
  const { lang } = useParams<{ lang: string }>();
  const isSupportedLocale = lang === "pl" || lang === "en";

  useEffect(() => {
    if (isSupportedLocale) {
      document.documentElement.lang = lang;
    }
  }, [isSupportedLocale, lang]);

  if (!isSupportedLocale) {
    return <Navigate to="/en/not-found" replace />;
  }

  return (
    <I18nProvider locale={lang as Locale}>
      <Outlet />
    </I18nProvider>
  );
};

export default LocaleRoute;
