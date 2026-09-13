import GradeButton from "@components/grades/gradeButton/GradeButton";
import useStudentScope from "@hooks/useStudentScope";
import type {
  Grade,
  LatestGradesByTeachingAssignment,
  TeachingAssignment,
} from "@types-local/index";
import styles from "./DashboardGrades.module.css";

type DashboardGradesProps = {
  grades: LatestGradesByTeachingAssignment;
};

const DashboardGrades = ({ grades }: DashboardGradesProps) => {
  const { teachingAssignments, activeStudent } = useStudentScope();

  if (!activeStudent || !teachingAssignments) {
    return null;
  }

  const filteredAssignments = teachingAssignments.filter(
    (assignment: TeachingAssignment) => {
      return (
        grades[assignment.id] && Object.keys(grades[assignment.id]).length > 0
      );
    },
  );

  return (
    <div className={styles.container}>
      {filteredAssignments.map((assignment: TeachingAssignment) => (
        <div key={assignment.id} className={styles.row}>
          <div>{assignment.subjectName}</div>
          <div className={styles.grades}>
            {grades[assignment.id]?.map((grade: Grade) => (
              <GradeButton
                key={grade.id}
                grade={grade}
                assignment={assignment}
              />
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};

export default DashboardGrades;
