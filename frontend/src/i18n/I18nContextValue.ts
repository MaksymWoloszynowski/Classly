import { createContext } from "react";
import type { Locale } from "../hooks/useLocalePath";
import type { TranslationKey } from "./translations";

export type I18nContextValue = {
  locale: Locale;
  t: (key: TranslationKey) => string;
};

const I18nContext = createContext<I18nContextValue | null>(null);

export default I18nContext;
