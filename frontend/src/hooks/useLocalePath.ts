import { useParams } from "react-router-dom";

export type Locale = "pl" | "en";

const polishPaths: Record<string, string> = {
  "/login": "/logowanie",
  "/register": "/rejestracja",
  "/unauthorized": "/brak-uprawnien",
  "/not-found": "/nie-znaleziono",
  "/student/dashboard": "/uczen/panel",
  "/student/grades": "/uczen/oceny",
  "/student/attendance": "/uczen/frekwencja",
  "/student/schedule": "/uczen/plan",
  "/student/assessments": "/uczen/sprawdziany",
  "/student/sessions": "/uczen/zajecia",
  "/teacher/dashboard": "/nauczyciel/panel",
  "/teacher/grades": "/nauczyciel/oceny",
  "/teacher/schedule": "/nauczyciel/plan",
  "/teacher/realizations": "/nauczyciel/realizacje",
  "/teacher/assessments": "/nauczyciel/sprawdziany",
  "/messages": "/wiadomosci",
  "/profile": "/profil",
  "/home": "/panel",
};

const englishPaths = Object.fromEntries(
  Object.entries(polishPaths).map(([englishPath, polishPath]) => [
    polishPath,
    englishPath,
  ]),
) as Record<string, string>;

export const useLocale = (): Locale => {
  const { lang } = useParams<{ lang: string }>();
  return lang === "pl" ? "pl" : "en";
};

const useLocalePath = () => {
  const locale = useLocale();

  return (path: string) => {
    const normalizedPath = path.startsWith("/") ? path : `/${path}`;
    const localizedPath =
      locale === "pl"
        ? (polishPaths[normalizedPath] ?? normalizedPath)
        : normalizedPath;

    return `/${locale}${localizedPath}`;
  };
};

export const getLocalizedPath = (path: string, locale: Locale) => {
  const normalizedPath = path.startsWith("/") ? path : `/${path}`;
  const localizedPath =
    locale === "pl"
      ? (polishPaths[normalizedPath] ?? normalizedPath)
      : (englishPaths[normalizedPath] ?? normalizedPath);

  return `/${locale}${localizedPath}`;
};

export const getCanonicalPath = (path: string, locale: Locale) => {
  const normalizedPath = path.startsWith("/") ? path : `/${path}`;

  return locale === "pl"
    ? (englishPaths[normalizedPath] ?? normalizedPath)
    : normalizedPath;
};

export default useLocalePath;
