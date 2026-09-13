import type { TeachingAssignment } from "../../../types/domain/teachingAssignment";
import GradeButton from "../gradeButton/GradeButton";
import styles from "./GradesRow.module.css";
import type { SemesterGrade } from "../../../types/domain/semesterGrade";
import type { SubjectGrades } from "../../../types/views/subjectGrades";
import type { Grade } from "@types-local/index";

interface GradesRowProps {
  grades: SubjectGrades;
  semesterGrades: SemesterGrade[];
  selectedSemester: number;
  assignment: TeachingAssignment;
  setSelectedGrade: (grade: Grade) => void;
  setAssignment: (assignment: TeachingAssignment) => void;
}

const GradesRow = ({
  grades,
  semesterGrades,
  selectedSemester,
  assignment,
  setSelectedGrade,
  setAssignment,
}: GradesRowProps) => {
  const proposedType =
    selectedSemester === 1 ? "PROPOSED_SEMESTER" : "PROPOSED_ANNUAL";
  const finalType = selectedSemester === 1 ? "FINAL_SEMESTER" : "FINAL_ANNUAL";

  return (
    <tr className={styles.row}>
      <td>{assignment.subjectName}</td>
      <td>
        <div className={styles.grades}>
          {grades?.grades.map((grade) => (
            <GradeButton
              grade={grade}
              assignment={assignment}
              key={grade.id}
              setSelectedGrade={setSelectedGrade}
              setAssignment={setAssignment}
            />
          ))}
        </div>
      </td>

      <td>{grades?.average.toPrecision(3)}</td>
      <td>
        {
          semesterGrades.find(
            (grade) =>
              grade.type === proposedType &&
              grade.teachingAssignmentId === assignment.id,
          )?.grade
        }
      </td>
      <td>
        {
          semesterGrades.find(
            (grade) =>
              grade.type === finalType &&
              grade.teachingAssignmentId === assignment.id,
          )?.grade
        }
      </td>
    </tr>
  );
};

export default GradesRow;
