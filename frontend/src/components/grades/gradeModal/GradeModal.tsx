import { X } from "lucide-react";
import type { Grade } from "../../../types/domain/grade";
import styles from "./GradeModal.module.css";
import type { TeachingAssignment } from "../../../types/domain/teachingAssignment";
import { formatDate } from "../../../utils/date";
import { createPortal } from "react-dom";

import useTeacher from "@hooks/useTeacher";

type GradeModalProps = {
  selectedGrade: Grade;
  assignment: TeachingAssignment | null;
  setSelectedGrade: (grade: Grade | null) => void;
  onDelete?: (categoryId: string, gradeId: string) => void;
  onUpdate?: () => void;
};

const GradeModal = ({
  selectedGrade,
  assignment,
  setSelectedGrade,
  onDelete,
  onUpdate,
}: GradeModalProps) => {
  const { isAssignmentTeacher } = useTeacher();

  return createPortal(
    <div className={styles.overlay} onClick={() => setSelectedGrade(null)}>
      <div className={styles.modal}>
        <div className={styles.header}>
          <div className={styles.headerTitle}>{assignment?.subjectName}</div>
          <button
            type="button"
            className={styles.close}
            onClick={() => setSelectedGrade(null)}
            aria-label="Close"
          >
            <X />
          </button>
        </div>
        <div className={styles.column}>
          <div className={styles.row}>Date:</div>
          <div className={styles.row}>
            {formatDate(new Date(selectedGrade.date))}
          </div>
        </div>
        <div className={styles.column}>
          <div className={styles.row}>Type:</div>
          <div className={styles.row}>
            {selectedGrade.type.replace("_", " ")}
          </div>
        </div>
        <div className={styles.column}>
          <div className={styles.row}>Description:</div>
          <div className={styles.row}>{selectedGrade.description}</div>
        </div>
        <div className={styles.column}>
          <div className={styles.row}>Weight:</div>
          <div className={styles.row}>{selectedGrade.weight}</div>
        </div>
        <div className={styles.column}>
          <div className={styles.row}>Grade:</div>
          <div
            className={`${styles.row} ${styles[selectedGrade.type.toLowerCase()]}`}
          >
            {selectedGrade.grade}
          </div>
        </div>
        {isAssignmentTeacher(assignment?.id || "") && (
          <div className={styles.footer}>
            <button type="button" className={styles.button} onClick={onUpdate}>
              Update
            </button>

            <button
              type="button"
              className={`${styles.button} ${styles.delete}`}
              onClick={() =>
                onDelete && onDelete(selectedGrade.categoryId, selectedGrade.id)
              }
            >
              Delete
            </button>
          </div>
        )}
      </div>
    </div>,
    document.body,
  );
};

export default GradeModal;
