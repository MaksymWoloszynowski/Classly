import { useTranslation } from "@hooks/useTranslation";
import type { AssessmentType, AttendanceType, GradeType } from "./enums";

export const useAssessmentTypeLabels = (): Record<AssessmentType, string> => {
  const { t } = useTranslation();

  return {
    TEST: t("test"),
    QUIZ: t("quiz"),
    CLASS_TEST: t("classTest"),
    HOMEWORK: t("homework"),
  };
};

export const useAttendanceTypeLabels = (): Record<AttendanceType, string> => {
  const { t } = useTranslation();

  return {
    PRESENT: t("present"),
    TARDY: t("tardy"),
    ABSENT: t("absent"),
    UNEXCUSED_ABSENCE: t("unexcusedAbsence"),
  };
};

export const useGradeTypeLabels = (): Record<GradeType, string> => {
  const { t } = useTranslation();

  return {
    CURRENT: t("current"),
    QUIZ: t("quiz"),
    HOMEWORK: t("homework"),
    TEST: t("test"),
    CLASS_TEST: t("classTest"),
    PARTICIPATION: t("participation"),
  };
};

export const useSubjectLabels = (): Record<string, string> => {
  const { t} = useTranslation()

  return {
    MATH: t("math"),
    POLISH: t("polishSubject"),
    ENGLISH: t("englishSubject"),
    HISTORY: t("history"),
    PHYSICS: t("physics"),
    CHEMISTRY: t("chemistry"),
    BIOLOGY: t("biology"),
    SPORTS: t("sports"),
    COMPUTER_SCIENCE: t("computerScience"),
    GEOGRAPHY: t("geography"),
    GERMAN: t("german"),
    MUSIC: t("music"),
    BUSINESS: t("business"),
  }
}