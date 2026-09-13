import type { Grade, TeachingAssignment } from "@types-local/index";
import styles from "./GradeButton.module.css";

type GradeButtonProps = {
  grade: Grade;
  assignment: TeachingAssignment;
  setSelectedGrade: (grade: Grade) => void;
  setAssignment: (assignment: TeachingAssignment) => void;
};

const GradeButton = ({
  grade,
  assignment,
  setSelectedGrade,
  setAssignment,
}: GradeButtonProps) => {
  return (
    <>
      <button
        onClick={() => {
          setSelectedGrade(grade);
          setAssignment(assignment);
        }}
        className={`${styles.grade} ${styles[grade.type.toLowerCase()]}`}
      >
        {grade.grade}
      </button>
    </>
  );
};

export default GradeButton;
