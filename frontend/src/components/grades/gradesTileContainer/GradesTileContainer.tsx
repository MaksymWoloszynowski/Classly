import useStudentScope from "@hooks/useStudentScope";
import type { Grade, SemesterGrade, StudentGradesByAssignment, TeachingAssignment } from "@types-local/index";
import GradesTile from "../gradesTile/GradesTile";

interface GradesTileContainerProps {
  grades: StudentGradesByAssignment;
  semesterGrades: SemesterGrade[];
  teachingAssignments: TeachingAssignment[];
  selectedSemester: number;
  setSelectedGrade: (grade: Grade) => void;
  setAssignment: (assignment: TeachingAssignment) => void;
}

const GradesTileContainer = ({
  grades,
  semesterGrades,
  teachingAssignments,
  selectedSemester,
  setSelectedGrade,
  setAssignment
}: GradesTileContainerProps) => {
  const { activeStudent } = useStudentScope();

  if (!activeStudent) {
    return null;
  }

  return (
    <div>
      {teachingAssignments.map((assignment) => (
        <GradesTile
          grades={grades[assignment.id]?.[activeStudent.id]}
          semesterGrades={semesterGrades}
          assignment={assignment}
          selectedSemester={selectedSemester}
          setSelectedGrade={setSelectedGrade}
          setAssignment={setAssignment}
          key={assignment.id}
        />
      ))}
    </div>
  );
};

export default GradesTileContainer;
