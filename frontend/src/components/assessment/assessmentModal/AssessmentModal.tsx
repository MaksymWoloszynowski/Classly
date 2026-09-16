import type { Assessment, AssessmentType } from "@types-local/index";
import { X } from "lucide-react";
import styles from "./AssessmentModal.module.css";
import { formatDate } from "@utils/date";
import useTeacher from "@hooks/useTeacher";
import { createPortal } from "react-dom";
import { useTranslation } from "../../../hooks/useTranslation";
import { useAssessmentTypeLabels } from "@types-local/labels";

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
  const { t } = useTranslation();
  const assessmentTypeLabels = useAssessmentTypeLabels();

  return createPortal(
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
            aria-label={t("close")}
          >
            <X />
          </button>
        </div>

        <div className={styles.subject}>{selectedAssessment.subjectName} · {t("group")} {selectedAssessment.groupName} </div>

        <div className={styles.info}>
          <div>
            <span>{t("date")}</span>

            <div>{formatDate(new Date(selectedAssessment.dateDue))}</div>
          </div>

          <div>
            <span>{t("teacher")}</span>

            <div>{selectedAssessment.teacherName}</div>
          </div>
        </div>

        {selectedAssessment.description && (
          <div className={styles.description}>
            <span>{t("description")}</span>

            <p>{selectedAssessment.description}</p>
          </div>
        )}

        {isAssignmentTeacher(selectedAssessment.teachingAssignmentId) && (
          <div className={styles.footer}>
            <button type="button" className={styles.button} onClick={onUpdate}>
              {t("update")}
            </button>

            <button
              type="button"
              className={`${styles.button} ${styles.delete}`}
              onClick={onDelete}
            >
              {t("delete")}
            </button>
          </div>
        )}
      </div>
    </div>,
    document.body,
  );
};

export default AssessmentModal;
