import type { Locale } from "../hooks/useLocalePath";
import I18nContext from "./I18nContextValue";
import { translations, type TranslationKey } from "./translations";

export const I18nProvider = ({
  locale,
  children,
}: {
  locale: Locale;
  children: React.ReactNode;
}) => {
  const dictionary = translations[locale];
  const t = (key: TranslationKey) => dictionary[key];

  return (
    <I18nContext.Provider value={{ locale, t }}>
      {children}
    </I18nContext.Provider>
  );
};
