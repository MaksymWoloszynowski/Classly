import useStudentScope from "@hooks/useStudentScope";
import type {

  Grade,
  SemesterGrade,
  StudentGradesByAssignment,
  TeachingAssignment,
} from "@types-local/index";
import styles from "./GradesTable.module.css";
import GradesRow from "../gradesRow/GradesRow";
import { useTranslation } from "@hooks/useTranslation";
interface GradesTableProps {
  grades: StudentGradesByAssignment;
  semesterGrades: SemesterGrade[];
  teachingAssignments: TeachingAssignment[];
  selectedSemester: number;
  setSelectedGrade: (grade: Grade) => void;
  setAssignment: (assignment: TeachingAssignment) => void;
}

const GradesTable = ({
  grades,
  semesterGrades,
  teachingAssignments,
  selectedSemester,
  setSelectedGrade,
  setAssignment
}: GradesTableProps) => {
  const { activeStudent } = useStudentScope();
  const { t } = useTranslation();

  if (!activeStudent) {
    return null;
  }

  return (
    <div className={styles.container}>
      <table className={styles.table}>
        <thead className={styles.tableHeader}>
          <tr>
            <th className={styles.tableHeaderInfo}>{t("subject")}</th>
            <th className={styles.tableHeaderInfo}>{t("grades")}</th>
            <th className={styles.tableHeaderInfo}>{t("semesterAverage")}</th>
            <th className={styles.tableHeaderInfo}>
              {selectedSemester === 1
                ? t("proposedSemesterGrade")
                : t("proposedAnnualGrade")}
            </th>
            <th className={styles.tableHeaderInfo}>
              {selectedSemester === 1
                ? t("finalSemesterGrade")
                : t("finalAnnualGrade")}
            </th>
          </tr>
        </thead>
        <tbody className={styles.tableBody}>
          {teachingAssignments.map((assignment) => (
            <GradesRow
              grades={grades[assignment.id]?.[activeStudent.id]}
              semesterGrades={semesterGrades}
              assignment={assignment}
              selectedSemester={selectedSemester}
              key={assignment.id}
              setSelectedGrade={setSelectedGrade}
              setAssignment={setAssignment}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default GradesTable;
