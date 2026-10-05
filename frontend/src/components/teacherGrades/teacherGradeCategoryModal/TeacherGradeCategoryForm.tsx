import { useState } from "react";
import api from "@api/api";
import type { GradeCategory, TeachingAssignment } from "@types-local/index";
import { gradeTypes, type GradeType } from "@types-local/enums";
import ErrorMessage from "@components/errorMessage/ErrorMessage";
import { useTranslation } from "@hooks/useTranslation";
import { useGradeTypeLabels } from "@types-local/labels";
import FormFooterButtons from "@components/formFooterButtons/FormFooterButtons";

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
  const {t} = useTranslation();
  const [description, setDescription] = useState(category?.description ?? "");
  const [type, setType] = useState<GradeType>(category?.type ?? "CURRENT");
  const [weight, setWeight] = useState(String(category?.weight ?? 1));
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const gradeTypeLabels = useGradeTypeLabels()

  const submit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const numericWeight = Number(weight);

    if (
      !Number.isFinite(numericWeight) ||
      numericWeight < 0 ||
      numericWeight > 3
    ) {
      setError("Enter a weight between 0 and 3.");
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
        {t("description")}
        <input
          value={description}
          onChange={(event) => setDescription(event.target.value)}
          autoFocus
        />
      </label>
      <label>
        {t("type")}
        <select
          value={type}
          onChange={(event) => setType(event.target.value as GradeType)}
        >
          {gradeTypes.map((gradeType) => (
            <option key={gradeType} value={gradeType}>
              {gradeTypeLabels[gradeType]}
            </option>
          ))}
        </select>
      </label>
      <label>
        {t("weight")}
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
      <FormFooterButtons
        onClose={onCancel}
        saveText={category ? t("saveChanges") : t("addCategory")}
        saving={saving}
      />
    </form>
  );
};

export default TeacherGradeCategoryForm;
