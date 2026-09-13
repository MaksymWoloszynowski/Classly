import type { Assessment, AssessmentType } from "@types-local/index";
import { X } from "lucide-react";
import styles from "./AssessmentModal.module.css";
import { formatDate } from "@utils/date";
import { assessmentTypeLabels } from "@types-local/labels";
import useTeacher from "@hooks/useTeacher";

const typeClass: Record<AssessmentType, string> = {
  TEST: styles.test,
  QUIZ: styles.quiz,
  CLASS_TEST: styles.classTest,
  HOMEWORK: styles.homework,
};

type AssessmentModalProps = {
  selectedAssessment: Assessment;
  setSelectedAssessment: (assessment: Assessment | null) => void;
  onDelete?: () => void;
  onUpdate?: () => void;
};

const AssessmentModal = ({
  selectedAssessment,
  setSelectedAssessment,
  onDelete,
  onUpdate,
}: AssessmentModalProps) => {
  const { isAssignmentTeacher } = useTeacher();

  return (
    <div className={styles.overlay} onClick={() => setSelectedAssessment(null)}>
      <div
        className={`${styles.details} ${typeClass[selectedAssessment.type]}`}
        onClick={(event) => event.stopPropagation()}
      >
        <div className={styles.header}>
          <span className={styles.type}>
            {assessmentTypeLabels[selectedAssessment.type]}
          </span>

          <button
            type="button"
            className={styles.close}
            onClick={() => setSelectedAssessment(null)}
            aria-label="Close"
          >
            <X />
          </button>
        </div>

        <h2>{selectedAssessment.subjectName}</h2>

        <div className={styles.info}>
          <div>
            <span>Date</span>

            <div>{formatDate(new Date(selectedAssessment.dateDue))}</div>
          </div>

          <div>
            <span>Teacher</span>

            <div>{selectedAssessment.teacherName}</div>
          </div>
        </div>

        {selectedAssessment.description && (
          <div className={styles.description}>
            <span>Description</span>

            <p>{selectedAssessment.description}</p>
          </div>
        )}

        {isAssignmentTeacher(selectedAssessment.teachingAssignmentId) && (
          <div className={styles.footer}>
            <button type="button" className={styles.button} onClick={onUpdate}>
              Update
            </button>

            <button
              type="button"
              className={`${styles.button} ${styles.delete}`}
              onClick={onDelete}
            >
              Delete
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default AssessmentModal;
