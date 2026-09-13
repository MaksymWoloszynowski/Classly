import api from "@api/api";
import LoadingOverlay from "@components/loadingOverlay/LoadingOverlay";
import TeacherGradesTable from "@components/teacherGrades/teacherGradesTable/TeacherGradesTable";
import TeacherGradesToolbar from "@components/teacherGrades/teacherGradesToolbar/TeacherGradesToolbar";
import useAuth from "@hooks/useAuth";
import {
  type ClassificationPeriod,
  type Grade,
  type GradeCategory,
  type GradeSummary,
  type SemesterGrade,
  type TeachingAssignment,
} from "@types-local/index";
import { useEffect, useState } from "react";
import styles from "../Grades.module.css";
import type { Group } from "@types-local/domain/group";
import type { StudentSummary } from "@types-local/domain/studentSummary";
import GradeModal from "@components/grades/gradeModal/GradeModal";
import TeacherGradeEditor from "@components/teacherGrades/teacherGradeEditor/TeacherGradeEditor";

const TeacherGrades = () => {
  const { auth } = useAuth();
  const assignments = auth?.teacher?.teachingAssignments ?? [];
  const [assignment, setAssignment] = useState<TeachingAssignment | null>(
    assignments[0],
  );
  const [periods, setPeriods] = useState<ClassificationPeriod[]>([]);
  const [periodId, setPeriodId] = useState("");
  const [students, setStudents] = useState<StudentSummary[]>([]);
  const [categories, setCategories] = useState<GradeCategory[]>([]);
  const [semesterGrades, setSemesterGrades] = useState<SemesterGrade[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);

  const [selectedGrade, setSelectedGrade] = useState<Grade | null>(null);
  const [gradeAssignment, setGradeAssignment] = useState<TeachingAssignment | null>(null)
  const [gradeToEdit, setGradeToEdit] = useState<Grade | null>(null);

  console.log(selectedGrade)
  console.log(gradeToEdit)

  useEffect(() => {
    if (
      assignments.length > 0 &&
      !assignments.some((assignment) => assignment.id === assignment.id)
    ) {
      setAssignment(assignments[0]);
    }
  }, [assignments, assignment]);

  useEffect(() => {
    const fetchPeriods = async () => {
      try {
        const response = await api.get<ClassificationPeriod[]>(
          "/api/classification-period",
        );
        setPeriods(response.data);
        setPeriodId((current) => current || response.data[0]?.id || "");
      } catch (error) {
        console.error("Error fetching classification periods:", error);
      }
    };

    fetchPeriods();
  }, []);

  useEffect(() => {
    if (!assignment || !periodId) return;

    const fetchGrades = async () => {
      setIsLoading(true);
      try {
        const [studentsResponse, categoriesResponse, semesterGradesResponse] =
          await Promise.all([
            api.get<Group>(`/api/group/${assignment.groupId}`),
            api.get<GradeCategory[]>(
              `/api/grade-category?teachingAssignmentId=${assignment.id}&classificationPeriod=${periodId}`,
            ),
            api.get<SemesterGrade[]>(
              `/api/semester-grade?groupId=${assignment.groupId}&classificationPeriod=${periodId}`,
            ),
          ]);
        setStudents(studentsResponse.data.students);
        setCategories(categoriesResponse.data);
        setSemesterGrades(semesterGradesResponse.data);
      } catch (error) {
        console.error("Error fetching teacher grades:", error);
        setStudents([]);
        setCategories([]);
        setSemesterGrades([]);
      } finally {
        setIsLoading(false);
      }
    };

    void fetchGrades();
  }, [assignment, periodId, assignments]);

  const handleGradesSaved = (
    categoryId: string,
    studentId: string,
    grade: GradeSummary,
  ) => {
    setCategories((current) =>
      current.map((category) => {
        if (category.id !== categoryId) return category;
        const withoutCurrent = category.grades.filter(
          (item) => item.id !== grade.id,
        );
        return {
          ...category,
          grades: [...withoutCurrent, { ...grade, studentId }],
        };
      }),
    );
  };

  const handleDeleteGrade = async (categoryId: string, gradeId: string) => {
    if (!selectedGrade) return;

    try {
      setIsLoading(true);
      await api.delete(`/api/grade/${selectedGrade.id}`);

      setCategories((current) =>
        current.map((category) =>
          category.id === categoryId
            ? {
                ...category,
                grades: category.grades.filter((grade) => grade.id !== gradeId),
              }
            : category,
        ),
      );

      setSelectedGrade(null);
    } catch (error) {
      console.error("Error deleting grade:", error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div>
      {isLoading && <LoadingOverlay />}
      <div className={styles.pageTitle}>Grades</div>
      <div>
        <TeacherGradesToolbar
          assignments={assignments}
          periods={periods}
          assignmentId={assignment?.id ?? ""}
          periodId={periodId}
          onAssignmentChange={(assignmentId) =>
            setAssignment(
              assignments.find((item) => item.id === assignmentId) ?? null,
            )
          }
          onPeriodChange={setPeriodId}
        />
        {assignment && periodId && (
          <TeacherGradesTable
            students={students}
            categories={categories}
            semesterGrades={semesterGrades}
            assignment={assignment}
            semester={
              periods.find((period) => period.id === periodId)?.semester ?? 1
            }
            setSelectedGrade={setSelectedGrade}
            onSaved={handleGradesSaved}
            setAssignment={setGradeAssignment}
            gradeToEdit={gradeToEdit}
            setGradeToEdit={setGradeToEdit}
          />
        )}

        {selectedGrade && (
          <GradeModal
            assignment={assignment}
            selectedGrade={selectedGrade}
            setSelectedGrade={setSelectedGrade}
            onDelete={handleDeleteGrade}
            onUpdate={() => {
                setGradeToEdit(selectedGrade);
                setSelectedGrade(null);
              }}
          />
        )}
      </div>
    </div>
  );
};

export default TeacherGrades;
