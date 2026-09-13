import type {
  ClassificationPeriod,
  TeachingAssignment,
} from "@types-local/index";
import styles from "./TeacherGradesToolbar.module.css";

type TeacherGradesToolbarProps = {
  assignments: TeachingAssignment[];
  periods: ClassificationPeriod[];
  assignmentId: string;
  periodId: string;
  onAssignmentChange: (assignmentId: string) => void;
  onPeriodChange: (periodId: string) => void;
};

const TeacherGradesToolbar = ({
  assignments,
  periods,
  assignmentId,
  periodId,
  onAssignmentChange,
  onPeriodChange,
}: TeacherGradesToolbarProps) => (
  <div className={styles.toolbar}>
    <label className={styles.control}>
      <span>Subject and group</span>
      <select
        value={assignmentId}
        onChange={(event) => onAssignmentChange(event.target.value)}
        disabled={assignments.length === 0}
      >
        {assignments.length === 0 && <option value="">No assignments</option>}
        {assignments.map((assignment) => (
          <option key={assignment.id} value={assignment.id}>
            {assignment.subjectName} · Group {assignment.groupName}
          </option>
        ))}
      </select>
    </label>
    <label className={styles.control}>
      <span>Classification period</span>
      <select
        value={periodId}
        onChange={(event) => onPeriodChange(event.target.value)}
        disabled={periods.length === 0}
      >
        {periods.map((period) => (
          <option key={period.id} value={period.id}>
            Semester {period.semester}
          </option>
        ))}
      </select>
    </label>
  </div>
);

export default TeacherGradesToolbar;
