import { useState } from "react";
import { createPortal } from "react-dom";
import { X } from "lucide-react";
import type { GradeCategory, TeachingAssignment } from "@types-local/index";
import styles from "./TeacherGradeCategoryModal.module.css";
import TeacherGradeCategoryForm from "./TeacherGradeCategoryForm";

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

  return createPortal(
    <div className={styles.overlay} onClick={onClose}>
      <div className={styles.modal} onClick={(event) => event.stopPropagation()}>
        <div className={styles.header}>
          <div>{category ? "Grade category" : "New grade category"}</div>
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
              <span>Description</span>
              <span>{category?.description}</span>
            </div>
            <div>
              <span>Type</span>
              <span>{category?.type.replace("_", " ")}</span>
            </div>
            <div>
              <span>Weight</span>
              <span>{category?.weight}</span>
            </div>
          </div>
        )}
        {!isEditing && (
          <div className={styles.actions}>
            <button type="button" onClick={onClose}>
              Cancel
            </button>
            {category && (
              <button
                type="button"
                onClick={(event) => {
                  event.preventDefault();
                  event.stopPropagation();
                  setIsEditing(true);
                }}
              >
                Edit
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
