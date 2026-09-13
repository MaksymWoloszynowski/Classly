import { useState } from "react";
import { X } from "lucide-react";
import api from "@api/api";
import {
  assessmentTypes,
  type Assessment,
  type AssessmentType,
} from "@types-local/index";
import type { TeachingAssignment } from "@types-local/domain/teachingAssignment";
import styles from "./AssessmentCreateModal.module.css";
import { assessmentTypeLabels } from "@types-local/labels";

type AssessmentCreateModalProps = {
  date: string;
  assignment: TeachingAssignment;
  assessment?: Assessment | null;
  onClose: () => void;
  onSaved: (assessment: Assessment) => void;
};

const AssessmentCreateModal = ({
  date,
  assignment,
  assessment,
  onClose,
  onSaved,
}: AssessmentCreateModalProps) => {
  const isEditing = Boolean(assessment);

  const [type, setType] = useState<AssessmentType | "">(assessment?.type ?? "");

  const [description, setDescription] = useState(assessment?.description ?? "");

  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const submit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (!type) return;

    try {
      setSaving(true);
      setError(null);

      const payload = {
        teachingAssignmentId: assignment.id,
        dateDue: date,
        type,
        description: description.trim() || null,
      };

      const response = isEditing
        ? await api.put<Assessment>(
            `/api/assessment/${assessment!.id}`,
            payload,
          )
        : await api.post<Assessment>("/api/assessment", payload);

      onSaved(response.data);
      onClose();
    } catch (error) {
      console.error(error);

      setError(
        isEditing ? "Error updating assessment" : "Error adding assessment",
      );
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className={styles.overlay} onClick={onClose}>
      <form
        className={styles.modal}
        onSubmit={submit}
        onClick={(event) => event.stopPropagation()}
      >
        <div className={styles.header}>
          <div>
            <div>{isEditing ? "Update assessment" : "Add assessment"}</div>

            <p>
              {assignment.subjectName} · {assignment.groupName}
            </p>

            <p>{date}</p>
          </div>

          <button
            type="button"
            className={styles.close}
            onClick={onClose}
            aria-label="Close"
          >
            <X />
          </button>
        </div>

        <label className={styles.label} htmlFor="assessment-type">
          Assessment type
        </label>

        <select
          id="assessment-type"
          className={styles.input}
          value={type}
          onChange={(event) => setType(event.target.value as AssessmentType)}
          required
        >
          <option value="">Choose a type</option>

          {assessmentTypes.map((assessmentType) => (
            <option key={assessmentType} value={assessmentType}>
              {assessmentTypeLabels[assessmentType]}
            </option>
          ))}
        </select>

        <label className={styles.label} htmlFor="assessment-description">
          Description (optional)
        </label>

        <textarea
          id="assessment-description"
          className={styles.textarea}
          value={description}
          onChange={(event) => setDescription(event.target.value)}
          maxLength={255}
        />

        {error && <p className={styles.error}>{error}</p>}

        <div className={styles.actions}>
          <button
            type="button"
            className={styles.cancel}
            onClick={onClose}
            disabled={saving}
          >
            Cancel
          </button>

          <button
            type="submit"
            className={styles.submit}
            disabled={saving || !type}
          >
            {saving
              ? "Saving..."
              : isEditing
                ? "Update assessment"
                : "Add assessment"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default AssessmentCreateModal;
