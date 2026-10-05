import { useState } from "react";
import { createPortal } from "react-dom";
import { X } from "lucide-react";
import api from "@api/api";
import type { Grade, GradeCategory, GradeSummary } from "@types-local/index";
import styles from "./TeacherGradeEditor.module.css";
import type { StudentSummary } from "@types-local/domain/studentSummary";
import ErrorMessage from "@components/errorMessage/ErrorMessage";

type TeacherGradeEditorProps = {
  student: StudentSummary;
  category: GradeCategory;
  grade?: GradeSummary | null;
  onClose: () => void;
  onSaved: (categoryId: string, studentId: string, grade: GradeSummary) => void;
};

const TeacherGradeEditor = ({
  student,
  category,
  grade,
  onClose,
  onSaved,
}: TeacherGradeEditorProps) => {
  const isEditing = Boolean(grade);

  const [saving, setSaving] = useState(false);
  const [gradeValue, setGradeValue] = useState<number | string>(
    grade?.grade ?? "",
  );
  const [error, setError] = useState<string | null>(null);

  const submit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const numericGrade = Number(gradeValue);

    if (
      gradeValue === "" ||
      Number.isNaN(numericGrade) ||
      numericGrade < 0 ||
      numericGrade > 100
    ) {
      setError("Grade must be between 0 and 100.");
      return;
    }

    try {
      setSaving(true);
      setError(null);

      const payload = {
        gradeCategoryId: category.id,
        grade: numericGrade,
        studentId: student.id,
      };

      const response = isEditing
        ? await api.put<Grade>(`/api/grade/${grade!.id}`, payload)
        : await api.post<Grade>("/api/grade", payload);

      onSaved(category.id, student.id, {
        id: response.data.id,
        grade: response.data.grade,
        date: response.data.date,
        studentId: student.id,
        studentFullName: response.data.studentFullName,
      } satisfies GradeSummary);
      onClose();
    } catch (error) {
      console.error(error);
      setError("Error saving grade.");
    } finally {
      setSaving(false);
    }
  };

  return createPortal(
    <div className={styles.overlay} onClick={onClose}>
      <form
        className={styles.modal}
        onSubmit={submit}
        onClick={(event) => event.stopPropagation()}
      >
        <div className={styles.editorHeader}>
          <div>
            {student.lastName} {student.firstName}
            <span>
              {category.description || category.type.replace("_", " ")}
            </span>
          </div>
          <button
            type="button"
            className={styles.iconButton}
            onClick={onClose}
            aria-label="Close"
          >
            <X size={18} />
          </button>
        </div>
        <label className={styles.editorField}>
          <span>Grade</span>
          <input
            autoFocus
            type="number"
            min="0"
            max="100"
            step="0.01"
            value={gradeValue}
            onChange={(event) => setGradeValue(event.target.value)}
          />
        </label>
        {error && <ErrorMessage message={error} />}

        <div className={styles.editorActions}>
          <button
            type="button"
            className={styles.secondaryButton}
            onClick={onClose}
            disabled={saving}
          >
            Cancel
          </button>
          <button
            type="submit"
            className={styles.primaryButton}
            disabled={saving}
          >
            {saving ? "Saving..." : "Save"}
          </button>
        </div>
      </form>
    </div>,
    document.body,
  );
};

export default TeacherGradeEditor;
