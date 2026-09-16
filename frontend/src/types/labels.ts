import { useTranslation } from "@hooks/useTranslation";
import type { AssessmentType, AttendanceType } from "./enums";

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
