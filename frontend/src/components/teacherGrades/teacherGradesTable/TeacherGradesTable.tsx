import type { SemesterGrade, TeachingAssignment } from "@types-local/index";
import type { GradeCategory, GradeSummary } from "@types-local/index";
import TeacherGradeCell from "../teacherGradeCell/TeacherGradeCell";
import styles from "./TeacherGradesTable.module.css";
import type { StudentSummary } from "@types-local/domain/studentSummary";

type TeacherGradesTableProps = {
  students: StudentSummary[];
  categories: GradeCategory[];
  semesterGrades: SemesterGrade[];
  assignment: TeachingAssignment;
  semester: number;
  setSelectedGrade: (grade: any) => void;
  setAssignment: (assignment: TeachingAssignment) => void;
  onSaved: (categoryId: string, studentId: string, grade: GradeSummary) => void;
  gradeToEdit: any
  setGradeToEdit: (grade: any) => void
};

const TeacherGradesTable = ({
  students,
  categories,
  semesterGrades,
  assignment,
  semester,
  setSelectedGrade,
  setAssignment,
  onSaved,
  gradeToEdit,
  setGradeToEdit
}: TeacherGradesTableProps) => {
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
    )?.grade ?? "-";

  return (
    <div className={styles.tableContainer}>
      <table className={styles.teacherTable}>
        <thead>
          <tr>
            <th className={styles.numberColumn}>No.</th>
            <th className={styles.studentColumn}>Student</th>
            {categories.map((category) => (
              <th key={category.id} className={styles.categoryColumn}>
                <span>
                  {category.description || category.type.replace("_", " ")}
                </span>
              </th>
            ))}
            <th>Average</th>
            <th>Proposed</th>
            <th>Final</th>
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
                  setSelectedGrade={setSelectedGrade}
                  onSaved={onSaved}
                  setAssignment={setAssignment}
                  setGradeToEdit={setGradeToEdit}
                  gradeToEdit={gradeToEdit}
                />
              ))}
              <td className={styles.summaryCell}>{getAverage(student.id)}</td>
              <td className={styles.summaryCell}>
                {getSemesterGrade(student.id, proposedType)}
              </td>
              <td className={styles.summaryCell}>
                {getSemesterGrade(student.id, finalType)}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {students.length === 0 && (
        <p className={styles.emptyState}>No students in group.</p>
      )}
    </div>
  );
};

export default TeacherGradesTable;
