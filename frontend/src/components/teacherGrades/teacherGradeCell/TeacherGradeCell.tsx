import type {
  Grade,
  GradeCategory,
  GradeSummary,
  TeachingAssignment,
} from "@types-local/index";
import { useState } from "react";
import api from "@api/api";
import GradeModal from "@components/grades/gradeModal/GradeModal";
import TeacherGradeEditor from "../teacherGradeEditor/TeacherGradeEditor";
import styles from "./TeacherGradeCell.module.css";
import type { StudentSummary } from "@types-local/domain/studentSummary";
import GradeButton from "@components/grades/gradeButton/GradeButton";
import ErrorMessage from "@components/errorMessage/ErrorMessage";

type TeacherGradeCellProps = {
  student: StudentSummary;
  category: GradeCategory;
  assignment: TeachingAssignment;
  onSaved: (categoryId: string, studentId: string, grade: GradeSummary) => void;
  onDeleted: (categoryId: string, gradeId: string) => void;
};

const TeacherGradeCell = ({
  student,
  category,
  assignment,
  onSaved,
  onDeleted,
}: TeacherGradeCellProps) => {
  const [selectedGrade, setSelectedGrade] = useState<Grade | null>(null);
  const [editingGrade, setEditingGrade] = useState<GradeSummary | null | undefined>(
    undefined,
  );
  const [error, setError] = useState<string | null>(null);
  const grades = category.grades.filter(
    (grade) => grade.studentId === student.id,
  );

  return (
    <>
      <td className={styles.gradeCell}>
        <div className={styles.gradeList}>
          {grades.map((grade) => (
            <GradeButton
              key={grade.id}
              grade={
                {
                  ...grade,
                  type: category.type,
                  categoryId: category.id,
                  description: category.description,
                  weight: category.weight,
                  studentFullName:
                    grade.studentFullName ??
                    `${student.firstName} ${student.lastName}`,
                  subjectName: assignment.subjectName,
                } satisfies Grade
              }
              assignment={assignment}
              onClick={setSelectedGrade}
            />
          ))}
          {grades.length === 0 && (
            <button
              type="button"
              className={styles.addGradeButton}
              onClick={() => setEditingGrade(null)}
              aria-label="Add grade"
            >
              +
            </button>
          )}
          {error && <ErrorMessage message={error} />}
        </div>
      </td>
      {editingGrade !== undefined && (
        <TeacherGradeEditor
          student={student}
          category={category}
          grade={editingGrade}
          onClose={() => setEditingGrade(undefined)}
          onSaved={onSaved}
        />
      )}
      {selectedGrade && (
        <>
        <GradeModal
          selectedGrade={selectedGrade}
          assignment={assignment}
          setSelectedGrade={setSelectedGrade}
          onUpdate={() => {
            setEditingGrade({
              id: selectedGrade.id,
              grade: selectedGrade.grade,
              date: selectedGrade.date,
              studentId: student.id,
              studentFullName: selectedGrade.studentFullName,
            });
            setSelectedGrade(null);
          }}
          onDelete={async (categoryId, gradeId) => {
            try {
              setError(null);
              await api.delete(`/api/grade/${gradeId}`);
              onDeleted(categoryId, gradeId);
              setSelectedGrade(null);
            } catch (deleteError) {
              console.error("Error deleting grade:", deleteError);
              setError("The grade could not be deleted.");
            }
          }}
        />
        </>
      )}
    </>
  );
};

export default TeacherGradeCell;
