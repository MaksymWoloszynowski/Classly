import type { Grade } from "../../../types/grade"

const GradeThing = ({ grade }: { grade: Grade }) => {
  return (
    <div>{grade.grade}</div>
  )
}

export default GradeThing