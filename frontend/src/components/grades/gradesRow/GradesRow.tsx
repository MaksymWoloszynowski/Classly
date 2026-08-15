import type { Grade } from "../../../types/grade";
import type { Subject } from "../../../types/subject";
import GradeThing from "../grade/GradeThing";

interface GradesRowProps {
  grades: Grade[];
  subject: Subject;
}

const GradesRow = ({ grades, subject }: GradesRowProps) => {
  return (
    <div>
      <h3>{subject.name}</h3>
      {grades.map((grade) => (
        <GradeThing key={grade.id} grade={grade} />
      ))}
    </div>
  );
};

export default GradesRow;
