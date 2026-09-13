import { type Dispatch, type SetStateAction } from "react";
import styles from "./GradesHeaderButtons.module.css";

interface GradesHeaderButtonsProps {
  selectedSemester: number;
  setSelectedSemester: Dispatch<SetStateAction<1 | 2>>;
}

const GradesHeaderButtons = ({
  selectedSemester,
  setSelectedSemester,
}: GradesHeaderButtonsProps) => {
  return (
    <div className={styles.tableHeader}>
      <button
        className={`${styles.button} ${selectedSemester === 1 && styles.active}`}
        onClick={() => setSelectedSemester(1)}
      >
        First Semester
      </button>
      <button
        className={`${styles.button} ${selectedSemester === 2 && styles.active}`}
        onClick={() => setSelectedSemester(2)}
      >
        Second Semester
      </button>
    </div>
  );
};

export default GradesHeaderButtons;
