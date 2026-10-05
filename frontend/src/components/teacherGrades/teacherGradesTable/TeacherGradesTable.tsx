import type { SemesterGrade, SemesterGradeType, TeachingAssignment } from "@types-local/index";
import type { GradeCategory, GradeSummary } from "@types-local/index";
import TeacherGradeCell from "../teacherGradeCell/TeacherGradeCell";
import styles from "./TeacherGradesTable.module.css";
import type { StudentSummary } from "@types-local/domain/studentSummary";
import { useTranslation } from "@hooks/useTranslation";

type TeacherGradesTableProps = {
  students: StudentSummary[];
  categories: GradeCategory[];
  semesterGrades: SemesterGrade[];
  assignment: TeachingAssignment;
  semester: number;
  onSaved: (categoryId: string, studentId: string, grade: GradeSummary) => void;
  onDeleted: (categoryId: string, gradeId: string) => void;
  onSemesterGradeEdit: (
    studentId: string,
    type: SemesterGradeType,
    grade?: SemesterGrade,
  ) => void;
  onCategoryEdit: (category: GradeCategory) => void;
};

const TeacherGradesTable = ({
  students,
  categories,
  semesterGrades,
  assignment,
  semester,
  onSaved,
  onDeleted,
  onSemesterGradeEdit,
  onCategoryEdit,
}: TeacherGradesTableProps) => {
  const { t } = useTranslation();
  const isFirstSemester = semester === 1;
  const proposedType = isFirstSemester
    ? "PROPOSED_SEMESTER"
    : "PROPOSED_ANNUAL";
  const finalType = isFirstSemester ? "FINAL_SEMESTER" : "FINAL_ANNUAL";

  const getAverage = (studentId: string) => {
    const entries = categories.flatMap((category) =>
      category.grades
        .filter((grade) => grade.studentId === studentId)
        .map((grade) => ({ value: grade.grade, weight: category.weight })),
    );
    const totalWeight = entries.reduce((sum, entry) => sum + entry.weight, 0);
    return totalWeight === 0
      ? "-"
      : (
          entries.reduce((sum, entry) => sum + entry.value * entry.weight, 0) /
          totalWeight
        ).toFixed(2);
  };

  const getSemesterGrade = (studentId: string, type: string) =>
    semesterGrades.find(
      (grade) =>
        grade.studentId === studentId &&
        grade.teachingAssignmentId === assignment.id &&
        grade.type === type,
    )?.grade ?? "+";

  return (
    <div className={styles.tableContainer}>
      <table className={styles.teacherTable}>
        <thead>
          <tr>
              <th className={styles.numberColumn}>{t("number")}</th>
              <th className={styles.studentColumn}>{t("student")}</th>
            {categories.map((category) => (
              <th key={category.id} className={styles.categoryColumn}>
                <button
                  type="button"
                  className={styles.categoryButton}
                  onClick={() => onCategoryEdit(category)}
                >
                  {category.description || category.type.replace("_", " ")}
                </button>
              </th>
            ))}
              <th>{t("average")}</th>
              <th>{t("proposedGrade")}</th>
              <th>{t("finalGrade")}</th>
          </tr>
        </thead>
        <tbody>
          {students.map((student, index) => (
            <tr key={student.id}>
              <td className={styles.numberColumn}>{index + 1}</td>
              <td className={styles.studentName}>
                 {student.lastName} {student.firstName}
              </td>
              {categories.map((category) => (
                <TeacherGradeCell
                  key={category.id}
                  student={student}
                  category={category}
                  assignment={assignment}
                  onSaved={onSaved}
                  onDeleted={onDeleted}
                />
              ))}
              <td className={styles.summaryCell}>{getAverage(student.id)}</td>
              <td className={styles.summaryCell}>
                <button
                  type="button"
                  className={styles.semesterGradeButton}
                  onClick={() =>
                    onSemesterGradeEdit(
                      student.id,
                      proposedType,
                      semesterGrades.find(
                        (grade) =>
                          grade.studentId === student.id &&
                        grade.teachingAssignmentId === assignment.id &&
                          grade.type === proposedType,
                      ),
                    )
                  }
                >
                  {getSemesterGrade(student.id, proposedType)}
                </button>
              </td>
              <td className={styles.summaryCell}>
                <button
                  type="button"
                  className={styles.semesterGradeButton}
                  onClick={() =>
                    onSemesterGradeEdit(
                      student.id,
                      finalType,
                      semesterGrades.find(
                        (grade) =>
                          grade.studentId === student.id &&
                        grade.teachingAssignmentId === assignment.id &&
                          grade.type === finalType,
                      ),
                    )
                  }
                >
                  {getSemesterGrade(student.id, finalType)}
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
        {students.length === 0 && (
          <p className={styles.emptyState}>{t("noStudentsInGroup")}</p>
        )}
    </div>
  );
};

export default TeacherGradesTable;
