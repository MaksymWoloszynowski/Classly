import { useState } from "react";
import type { Grade } from "../../../types/grade";
import type { Subject } from "../../../types/subject";
import GradesRow from "../gradesRow/GradesRow";

interface GradesTableProps {
  firstSemesterGrades: Grade[];
  secondSemesterGrades: Grade[];
  annualGrades: Grade[];
  subjects: Subject[];
}

const GradesTable = ({
  firstSemesterGrades,
  secondSemesterGrades,
  annualGrades,
  subjects
}: GradesTableProps) => {
  const [selectedSemester, setSelectedSemester] = useState<1 | 2>(1);

  const displayedGrades = selectedSemester === 1 ? firstSemesterGrades : secondSemesterGrades;

  return (
    <div>
        <div>
          <button onClick={() => setSelectedSemester(1)}>First Semester</button>
          <button onClick={() => setSelectedSemester(2)}>Second Semester</button>
        </div>
        <div>
            <p>Subject</p>
            <p>Grades</p>
            <p>Semester average</p>
            <p>{selectedSemester === 1 ? "Proposed semester grade" : "Proposed annual grade"}</p>
            <p>{selectedSemester === 1 ? "Final semester grade" : "Final annual grade"}</p>
        </div>
      {subjects.map((subject) => (
        <GradesRow
          grades={displayedGrades.filter(
            (grade) => grade.subjectId === subject.id,
          )}
          subject={subject}
          key={subject.id}
        />
      ))}
    </div>
  );
};

export default GradesTable;
