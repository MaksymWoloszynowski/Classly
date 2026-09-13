import { useState } from "react";
import type {
  Grade,
  GradeCategory,
  GradeSummary,
  TeachingAssignment,
} from "@types-local/index";
import TeacherGradeEditor from "../teacherGradeEditor/TeacherGradeEditor";
import styles from "./TeacherGradeCell.module.css";
import type { StudentSummary } from "@types-local/domain/studentSummary";
import GradeButton from "@components/grades/gradeButton/GradeButton";

type TeacherGradeCellProps = {
  student: StudentSummary;
  category: GradeCategory;
  assignment: TeachingAssignment;
  setSelectedGrade: (grade: any) => void
  setAssignment: (assignment: TeachingAssignment) => void
  onSaved: (categoryId: string, studentId: string, grade: GradeSummary) => void;
  setGradeToEdit: (grade: any) => void
  gradeToEdit: any;
};

const TeacherGradeCell = ({
  student,
  category,
  assignment,
  setSelectedGrade,
  setAssignment,
  onSaved,
  setGradeToEdit,
  gradeToEdit
}: TeacherGradeCellProps) => {

  const grades = category.grades.filter(
    (grade) => grade.studentId === student.id,
  );

  return (
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
            setSelectedGrade={setSelectedGrade}
            assignment={assignment}
            setAssignment={setAssignment}
          />
        ))}
        {grades.length === 0 && (
          <button
            type="button"
            className={styles.addGradeButton}
            onClick={() => setGradeToEdit(null)}
            aria-label="Add grade"
          >
            +
          </button>
        )}
      </div>

      {gradeToEdit && (
        <TeacherGradeEditor
          student={student}
          category={category}
          grade={gradeToEdit}
          onClose={() => setGradeToEdit(null)}
          onSaved={onSaved}
        />
      )}
    </td>
  );
};

export default TeacherGradeCell;
