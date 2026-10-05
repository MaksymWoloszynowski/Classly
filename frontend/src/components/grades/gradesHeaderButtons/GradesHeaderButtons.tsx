import { type Dispatch, type SetStateAction } from "react";
import styles from "./GradesHeaderButtons.module.css";
import { useTranslation } from "@hooks/useTranslation";

interface GradesHeaderButtonsProps {
  selectedSemester: number;
  setSelectedSemester: Dispatch<SetStateAction<1 | 2>>;
}

const GradesHeaderButtons = ({
  selectedSemester,
  setSelectedSemester,
}: GradesHeaderButtonsProps) => {
  const { t } = useTranslation();

  return (
    <div className={styles.tableHeader}>
      <button
        className={`${styles.button} ${selectedSemester === 1 && styles.active}`}
        onClick={() => setSelectedSemester(1)}
      >
        {t("firstSemester")}
      </button>
      <button
        className={`${styles.button} ${selectedSemester === 2 && styles.active}`}
        onClick={() => setSelectedSemester(2)}
      >
        {t("secondSemester")}
      </button>
    </div>
  );
};

export default GradesHeaderButtons;
