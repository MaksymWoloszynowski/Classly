import type { TeachingAssignment } from "../../../types/domain/teachingAssignment";
import styles from "./GradesTile.module.css";
import type { SemesterGrade } from "../../../types/domain/semesterGrade";
import GradeButton from "../gradeButton/GradeButton";
import type { SubjectGrades } from "../../../types/views/subjectGrades";
import type { Grade } from "@types-local/index";

interface GradesTileProps {
  grades: SubjectGrades;
  semesterGrades: SemesterGrade[];
  assignment: TeachingAssignment;
  selectedSemester: number;
  setSelectedGrade: (grade: Grade) => void;
  setAssignment: (assignment: TeachingAssignment) => void;
}

const GradesTile = ({
  grades,
  semesterGrades,
  assignment,
  selectedSemester,
  setSelectedGrade,
  setAssignment,
}: GradesTileProps) => {
  const proposedType =
    selectedSemester === 1 ? "PROPOSED_SEMESTER" : "PROPOSED_ANNUAL";
  const finalType = selectedSemester === 1 ? "FINAL_SEMESTER" : "FINAL_ANNUAL";

  return (
    <div className={styles.tile}>
      <div className={styles.tileTitle}>{assignment.subjectName}</div>
      <div className={`${styles.tileContent} ${styles.grades}`}>
        {grades?.grades.map((grade) => (
          <GradeButton
            grade={grade}
            assignment={assignment}
            key={grade.id}
            setSelectedGrade={setSelectedGrade}
            setAssignment={setAssignment}
          />
        ))}
      </div>
      <div className={styles.tileContent}>
        <div className={styles.row}>
          <div className={styles.column}>Semester average</div>
          <div className={styles.column}>{grades?.average.toPrecision(3)}</div>
        </div>
        <div className={styles.row}>
          <div className={styles.column}>
            {selectedSemester === 1
              ? "Proposed semester grade"
              : "Proposed annual grade"}
          </div>
          <div className={styles.column}>
            {
              semesterGrades.find(
                (grade) =>
                  grade.type === proposedType &&
                  grade.teachingAssignmentId === assignment.id,
              )?.grade
            }
          </div>
        </div>
        <div className={styles.row}>
          <div className={styles.column}>
            {selectedSemester === 1
              ? "Final semester grade"
              : "Final annual grade"}
          </div>
          <div className={styles.column}>
            {
              semesterGrades.find(
                (grade) =>
                  grade.type === finalType &&
                  grade.teachingAssignmentId === assignment.id,
              )?.grade
            }
          </div>
        </div>
      </div>
    </div>
  );
};

export default GradesTile;
