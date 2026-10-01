import { useState } from "react";
import { createPortal } from "react-dom";
import { X } from "lucide-react";
import type { GradeCategory, TeachingAssignment } from "@types-local/index";
import styles from "./TeacherGradeCategoryModal.module.css";
import TeacherGradeCategoryForm from "./TeacherGradeCategoryForm";
import { useTranslation } from "@hooks/useTranslation";
import { useGradeTypeLabels } from "@types-local/labels";

type TeacherGradeCategoryModalProps = {
  assignment: TeachingAssignment;
  periodId: string;
  category?: GradeCategory;
  onClose: () => void;
  onCreated?: (category: GradeCategory) => void;
  onUpdated?: (category: GradeCategory) => void;
};

const TeacherGradeCategoryModal = ({
  assignment,
  periodId,
  category,
  onClose,
  onCreated,
  onUpdated,
}: TeacherGradeCategoryModalProps) => {
  const [isEditing, setIsEditing] = useState(!category);
  const {t} = useTranslation();
  const gradeTypeLabels = useGradeTypeLabels()

  return createPortal(
    <div className={styles.overlay} onClick={onClose}>
      <div className={styles.modal} onClick={(event) => event.stopPropagation()}>
        <div className={styles.header}>
          <div>{category ? t("gradeCategory") : t("newGradeCategory")}</div>
          <button
            type="button"
            className={styles.close}
            onClick={onClose}
            aria-label="Close"
          >
            <X size={18} />
          </button>
        </div>
        {isEditing ? (
          <TeacherGradeCategoryForm
            assignment={assignment}
            periodId={periodId}
            category={category}
            onCancel={onClose}
            onCreated={onCreated}
            onUpdated={onUpdated}
          />
        ) : (
          <div className={styles.details}>
            <div>
              <span>{t("description")}</span>
              <span>{category?.description}</span>
            </div>
            <div>
              <span>{t("type")}</span>
              <span>{category && gradeTypeLabels[category.type]}</span>
            </div>
            <div>
              <span>{t("weight")}</span>
              <span>{category?.weight}</span>
            </div>
          </div>
        )}
        {!isEditing && (
          <div className={styles.actions}>
            {category && (
              <button
                type="button"
                onClick={(event) => {
                  event.preventDefault();
                  event.stopPropagation();
                  setIsEditing(true);
                }}
              >
                {t("edit")}
              </button>
            )}
          </div>
        )}
      </div>
    </div>,
    document.body,
  );
};

export default TeacherGradeCategoryModal;
