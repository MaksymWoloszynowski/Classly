import { useState } from "react";
import api from "@api/api";
import type { GradeCategory, TeachingAssignment } from "@types-local/index";
import { gradeTypes, type GradeType } from "@types-local/enums";
import styles from "./TeacherGradeCategoryModal.module.css";
import ErrorMessage from "@components/errorMessage/ErrorMessage";

type TeacherGradeCategoryFormProps = {
  assignment: TeachingAssignment;
  periodId: string;
  category?: GradeCategory;
  onCancel: () => void;
  onCreated?: (category: GradeCategory) => void;
  onUpdated?: (category: GradeCategory) => void;
};

const TeacherGradeCategoryForm = ({
  assignment,
  periodId,
  category,
  onCancel,
  onCreated,
  onUpdated,
}: TeacherGradeCategoryFormProps) => {
  const [description, setDescription] = useState(category?.description ?? "");
  const [type, setType] = useState<GradeType>(category?.type ?? "CURRENT");
  const [weight, setWeight] = useState(String(category?.weight ?? 1));
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const submit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const numericWeight = Number(weight);

    if (
      !description.trim() ||
      !Number.isFinite(numericWeight) ||
      numericWeight < 0 ||
      numericWeight > 3
    ) {
      setError("Enter a description and a weight between 0 and 3.");
      return;
    }

    try {
      setSaving(true);
      setError(null);
      const payload = {
        teachingAssignmentId: assignment.id,
        classificationPeriod: periodId,
        description: description.trim(),
        type,
        weight: numericWeight,
      };
      const response = category
        ? await api.put<GradeCategory>(
            `/api/grade-category/${category.id}`,
            payload,
          )
        : await api.post<GradeCategory>("/api/grade-category", payload);

      if (category) {
        onUpdated?.(response.data);
      } else {
        onCreated?.(response.data);
      }
    } catch (saveError) {
      console.error("Error saving grade category:", saveError);
      setError("Error saving category.");
    } finally {
      setSaving(false);
    }
  };

  return (
    <form
      onSubmit={submit}
      onClick={(event) => event.stopPropagation()}
    >
      <label>
        Description
        <input
          value={description}
          onChange={(event) => setDescription(event.target.value)}
          autoFocus
        />
      </label>
      <label>
        Type
        <select
          value={type}
          onChange={(event) => setType(event.target.value as GradeType)}
        >
          {gradeTypes.map((gradeType) => (
            <option key={gradeType} value={gradeType}>
              {gradeType.replace("_", " ")}
            </option>
          ))}
        </select>
      </label>
      <label>
        Weight
        <input
          type="number"
          min="0"
          max="3"
          step="1"
          value={weight}
          onChange={(event) => setWeight(event.target.value)}
        />
      </label>
      {error && <ErrorMessage message={error} />}
      <div className={styles.actions}>
        <button type="button" onClick={onCancel} disabled={saving}>
          Cancel
        </button>
        <button type="submit" disabled={saving}>
          {saving ? "Saving..." : category ? "Save changes" : "Add category"}
        </button>
      </div>
    </form>
  );
};

export default TeacherGradeCategoryForm;
