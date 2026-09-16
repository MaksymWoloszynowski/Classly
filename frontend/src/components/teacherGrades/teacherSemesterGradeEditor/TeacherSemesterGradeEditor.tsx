import { useState } from "react";
import { createPortal } from "react-dom";
import { X } from "lucide-react";
import api from "@api/api";
import type {
  SemesterGrade,
  SemesterGradeType,
  TeachingAssignment,
} from "@types-local/index";
import type { StudentSummary } from "@types-local/domain/studentSummary";
import styles from "./TeacherSemesterGradeEditor.module.css";
import ErrorMessage from "@components/errorMessage/ErrorMessage";

type TeacherSemesterGradeEditorProps = {
  assignment: TeachingAssignment;
  periodId: string;
  studentId: string;
  student?: StudentSummary;
  type: SemesterGradeType;
  grade?: SemesterGrade;
  onClose: () => void;
  onSaved: (grade: SemesterGrade) => void;
  onDeleted: (grade: SemesterGrade) => void;
};

const TeacherSemesterGradeEditor = ({
  assignment,
  periodId,
  studentId,
  student,
  type,
  grade,
  onClose,
  onSaved,
  onDeleted,
}: TeacherSemesterGradeEditorProps) => {
  const [value, setValue] = useState<number | string>(grade?.grade ?? "");
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const isEditing = Boolean(grade);
  const title = type.startsWith("PROPOSED") ? "Proposed grade" : "Final grade";

  const remove = async () => {
    if (!grade) return;

    try {
      setSaving(true);
      setError(null);
      await api.delete(`/api/semester-grade/${grade.id}`);
      onDeleted(grade);
      onClose();
    } catch (deleteError) {
      console.error("Error deleting semester grade:", deleteError);
      setError("Error deleting grade.");
    } finally {
      setSaving(false);
    }
  };

  const submit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const numericGrade = Number(value);
    if (
      value === "" ||
      !Number.isFinite(numericGrade) ||
      numericGrade < 1 ||
      numericGrade > 6
    ) {
      setError("Grade must be between 1 and 6.");
      return;
    }

    try {
      setSaving(true);
      setError(null);
      const payload = {
        grade: numericGrade,
        type,
        studentId,
        teachingAssignmentId: assignment.id,
        classificationPeriod: periodId,
      };
      const response = isEditing
        ? await api.put<SemesterGrade>(
            `/api/semester-grade/${grade!.id}`,
            payload,
          )
        : await api.post<SemesterGrade>("/api/semester-grade", payload);
      onSaved(response.data);
    } catch (saveError) {
      console.error("Error saving semester grade:", saveError);
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
        <div className={styles.header}>
          <div>
            <div className={styles.title}>{title}</div>
            <span>
              {student ? `${student.lastName} ${student.firstName}` : "Student"}
            </span>
          </div>
          <button
            type="button"
            className={styles.close}
            onClick={onClose}
            aria-label="Close"
          >
            <X size={18} />
          </button>
        </div>
        <label>
          Grade
          <input
            autoFocus
            type="number"
            min="1"
            max="6"
            step="1"
            value={value}
            onChange={(event) => setValue(event.target.value)}
          />
        </label>
        {error && <ErrorMessage message={error} />}
        <div className={styles.actions}>
          <div>
            {grade && (
              <button
                type="button"
                className={`${styles.button} ${styles.delete}`}
                onClick={() => void remove()}
                disabled={saving}
              >
                Delete
              </button>
            )}
          </div>
          <div>
            <button type="button" onClick={onClose} disabled={saving}>
              Cancel
            </button>
            <button type="submit" disabled={saving}>
              {saving ? "Saving..." : "Save"}
            </button>
          </div>
        </div>
      </form>
    </div>,
    document.body,
  );
};

export default TeacherSemesterGradeEditor;
