import type {
  ClassificationPeriod,
  TeachingAssignment,
} from "@types-local/index";
import styles from "./TeacherGradesToolbar.module.css";
import { useTranslation } from "@hooks/useTranslation";
import { useSubjectLabels } from "@types-local/labels";
import { formatDate } from "@fullcalendar/react";

type TeacherGradesToolbarProps = {
  assignments: TeachingAssignment[];
  periods: ClassificationPeriod[];
  assignmentId: string;
  periodId: string;
  onAssignmentChange: (assignmentId: string) => void;
  onPeriodChange: (periodId: string) => void;
  onAddCategory: () => void;
};

const TeacherGradesToolbar = ({
  assignments,
  periods,
  assignmentId,
  periodId,
  onAssignmentChange,
  onPeriodChange,
  onAddCategory,
}: TeacherGradesToolbarProps) => {
  const { t } = useTranslation();
  const subjectLabels = useSubjectLabels()

  return (
  <div className={styles.toolbar}>
    <label className={styles.control}>
        <span>{t("subjectAndGroup")}</span>
      <select
        value={assignmentId}
        onChange={(event) => onAssignmentChange(event.target.value)}
        disabled={assignments.length === 0}
      >
          {assignments.length === 0 && <option value="">{t("noAssignments")}</option>}
        {assignments.map((assignment) => (
          <option key={assignment.id} value={assignment.id}>
            {subjectLabels[formatDate(assignment.subjectName)]} · {t("group")} {assignment.groupName}
          </option>
        ))}
      </select>
    </label>
    <label className={styles.control}>
        <span>{t("classificationPeriod")}</span>
      <select
        value={periodId}
        onChange={(event) => onPeriodChange(event.target.value)}
        disabled={periods.length === 0}
      >
        {periods.map((period) => (
          <option key={period.id} value={period.id}>
              {t("semester")} {period.semester}
          </option>
        ))}
      </select>
    </label>
    <button
      type="button"
      className={styles.actionButton}
      onClick={onAddCategory}
      disabled={!assignmentId || !periodId}
    >
        {t("addCategory")}
    </button>
  </div>
  );
};

export default TeacherGradesToolbar;
