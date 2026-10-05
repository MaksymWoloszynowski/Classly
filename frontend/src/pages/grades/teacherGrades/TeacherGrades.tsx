import api from "@api/api";
import LoadingOverlay from "@components/loadingOverlay/LoadingOverlay";
import ErrorMessage from "@components/errorMessage/ErrorMessage";
import TeacherGradesTable from "@components/teacherGrades/teacherGradesTable/TeacherGradesTable";
import TeacherGradesToolbar from "@components/teacherGrades/teacherGradesToolbar/TeacherGradesToolbar";
import useAuth from "@hooks/useAuth";
import {
  type ClassificationPeriod,
  type GradeCategory,
  type GradeSummary,
  type SemesterGrade,
  type SemesterGradeType,
  type TeachingAssignment,
} from "@types-local/index";
import { useEffect, useState } from "react";
import styles from "../Grades.module.css";
import type { Group } from "@types-local/domain/group";
import type { StudentSummary } from "@types-local/domain/studentSummary";
import TeacherGradeCategoryModal from "@components/teacherGrades/teacherGradeCategoryModal/TeacherGradeCategoryModal";
import TeacherSemesterGradeEditor from "@components/teacherGrades/teacherSemesterGradeEditor/TeacherSemesterGradeEditor";
import { useTranslation } from "../../../hooks/useTranslation";

const TeacherGrades = () => {
  const { auth } = useAuth();
  const { t } = useTranslation();
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
  const [error, setError] = useState<string | null>(null);
  const [isCategoryModalOpen, setIsCategoryModalOpen] = useState(false);
  const [categoryToEdit, setCategoryToEdit] = useState<GradeCategory | null>(null);
  const [semesterGradeToEdit, setSemesterGradeToEdit] = useState<{
    studentId: string;
    type: SemesterGradeType;
    grade?: SemesterGrade;
  } | null>(null);
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
        setError("Classification periods could not be loaded.");
      }
    };

    fetchPeriods();
  }, []);

  useEffect(() => {
    if (!assignment || !periodId) return;

    const fetchGrades = async () => {
      setIsLoading(true);
      setError(null);
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
        setError("Grade data could not be loaded.");
        setStudents([]);
        setCategories([]);
        setSemesterGrades([]);
      } finally {
        setIsLoading(false);
      }
    };

    fetchGrades();
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

  const handleGradeDeleted = (categoryId: string, gradeId: string) => {
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
  };

  const handleSemesterGradeSaved = (savedGrade: SemesterGrade) => {
    setSemesterGrades((current) => [
      ...current.filter((grade) => grade.id !== savedGrade.id && !(
        grade.studentId === savedGrade.studentId && grade.type === savedGrade.type
      )),
      savedGrade,
    ]);
  };

  const handleSemesterGradeDeleted = (deletedGrade: SemesterGrade) => {
    setSemesterGrades((current) =>
      current.filter((grade) => grade.id !== deletedGrade.id),
    );
  };

  const handleCategoryUpdated = (updatedCategory: GradeCategory) => {
    setCategories((current) =>
      current.map((category) =>
        category.id === updatedCategory.id
          ? {
              ...category,
              ...updatedCategory,
              grades: updatedCategory.grades ?? category.grades,
            }
          : category,
      ),
    );
  };

  return (
    <div>
      {isLoading && <LoadingOverlay />}
      {error && <ErrorMessage message={error} />}
      <div className={styles.pageTitle}>{t("grades")}</div>
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
          onAddCategory={() => setIsCategoryModalOpen(true)}
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
            onSaved={handleGradesSaved}
            onDeleted={handleGradeDeleted}
            onSemesterGradeEdit={(studentId, type, grade) =>
              setSemesterGradeToEdit({ studentId, type, grade })
            }
            onCategoryEdit={setCategoryToEdit}
          />
        )}

        {isCategoryModalOpen && assignment && (
          <TeacherGradeCategoryModal
            assignment={assignment}
            periodId={periodId}
            onClose={() => setIsCategoryModalOpen(false)}
            onCreated={(category) => {
              setCategories((current) => [...current, category]);
              setIsCategoryModalOpen(false);
            }}
          />
        )}

        {categoryToEdit && assignment && (
          <TeacherGradeCategoryModal
            assignment={assignment}
            periodId={periodId}
            category={categoryToEdit}
            onClose={() => setCategoryToEdit(null)}
            onUpdated={(updatedCategory) => {
              handleCategoryUpdated(updatedCategory);
              setCategoryToEdit(null);
            }}
          />
        )}

        {semesterGradeToEdit && assignment && (
          <TeacherSemesterGradeEditor
            assignment={assignment}
            periodId={periodId}
            studentId={semesterGradeToEdit.studentId}
            type={semesterGradeToEdit.type}
            grade={semesterGradeToEdit.grade}
            student={students.find((item) => item.id === semesterGradeToEdit.studentId)}
            onClose={() => setSemesterGradeToEdit(null)}
            onSaved={(savedGrade) => {
              handleSemesterGradeSaved(savedGrade);
              setSemesterGradeToEdit(null);
            }}
            onDeleted={handleSemesterGradeDeleted}
          />
        )}
      </div>
    </div>
  );
};

export default TeacherGrades;
