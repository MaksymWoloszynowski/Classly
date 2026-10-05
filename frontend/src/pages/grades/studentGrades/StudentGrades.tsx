import api from "@api/api";
import GradesHeaderButtons from "@components/grades/gradesHeaderButtons/GradesHeaderButtons";
import GradesTable from "@components/grades/gradesTable/GradesTable";
import GradesTileContainer from "@components/grades/gradesTileContainer/GradesTileContainer";
import LoadingOverlay from "@components/loadingOverlay/LoadingOverlay";
import ErrorMessage from "@components/errorMessage/ErrorMessage";
import useStudentScope from "@hooks/useStudentScope";
import { useMediaQuery } from "@mui/material";
import {
  type ClassificationPeriod,
  type Grade,
  type SemesterGrade,
  type StudentGradesByAssignment,
  type TeachingAssignment,
} from "@types-local/index";
import { useEffect, useState } from "react";
import styles from "../Grades.module.css";
import GradeModal from "@components/grades/gradeModal/GradeModal";
import { useTranslation } from "../../../hooks/useTranslation";

const StudentGrades = () => {
  const isMobile = useMediaQuery("(max-width:1000px)");
  const [selectedSemester, setSelectedSemester] = useState<1 | 2>(1);
  const [firstSemesterGrades, setFirstSemesterGrades] =
    useState<StudentGradesByAssignment>({});
  const [secondSemesterGrades, setSecondSemesterGrades] =
    useState<StudentGradesByAssignment>({});
  const [semesterGrades, setSemesterGrades] = useState<SemesterGrade[]>([]);
  const [annualGrades, setAnnualGrades] = useState<SemesterGrade[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const { activeStudent, isLoadingStudent, teachingAssignments } =
    useStudentScope();
  const { t } = useTranslation();

  const [selectedGrade, setSelectedGrade] = useState<Grade | null>(null);
  const [assignment, setAssignment] = useState<TeachingAssignment | null>(null);

  const displayedGrades =
    selectedSemester === 1 ? firstSemesterGrades : secondSemesterGrades;

  const displayedSemesterGrades =
    selectedSemester === 1 ? semesterGrades : annualGrades;

  const fetchClassificationPeriods = async () => {
    try {
      const response = await api.get<ClassificationPeriod[]>(
        "/api/classification-period",
      );

      return response.data;
    } catch (error) {
      console.error("Error fetching classification periods:", error);
      return [];
    }
  };

  const fetchFirstSemesterGrades = async (periods: ClassificationPeriod[]) => {
    const response = await api.get<StudentGradesByAssignment>(
      `/api/grade?studentId=${activeStudent?.id}&classificationPeriod=${periods[0]?.id}`,
    );

    return response.data;
  };

  const fetchSecondSemesterGrades = async (periods: ClassificationPeriod[]) => {
    const response = await api.get<StudentGradesByAssignment>(
      `/api/grade?studentId=${activeStudent?.id}&classificationPeriod=${periods[1]?.id}`,
    );

    return response.data;
  };

  const fetchSemesterGrades = async (periods: ClassificationPeriod[]) => {
    const response = await api.get<SemesterGrade[]>(
      `/api/semester-grade?studentId=${activeStudent?.id}&classificationPeriod=${periods[0]?.id}`,
    );

    return response.data;
  };

  const fetchAnnualGrades = async (periods: ClassificationPeriod[]) => {
    const response = await api.get<SemesterGrade[]>(
      `/api/semester-grade?studentId=${activeStudent?.id}&classificationPeriod=${periods[1]?.id}`,
    );

    return response.data;
  };

  useEffect(() => {
    const fetchData = async () => {
      if (!activeStudent) return;

      setIsLoading(true);

      try {
        const periods = await fetchClassificationPeriods();

        if (periods.length < 2) {
          return;
        }

        const [
          firstSemesterGrades,
          secondSemesterGrades,
          semesterGrades,
          annualGrades,
        ] = await Promise.all([
          fetchFirstSemesterGrades(periods),
          fetchSecondSemesterGrades(periods),
          fetchSemesterGrades(periods),
          fetchAnnualGrades(periods),
        ]);

        setFirstSemesterGrades(firstSemesterGrades);
        setSecondSemesterGrades(secondSemesterGrades);
        setSemesterGrades(semesterGrades);
        setAnnualGrades(annualGrades);
      } catch (error) {
        console.error(error);
        setError("Grades could not be loaded.");
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, [activeStudent?.id]);

  return (
    <div>
      {(isLoading || isLoadingStudent) && <LoadingOverlay />}
      {error && <ErrorMessage message={error} />}
      <div className={styles.pageTitle}>{t("grades")}</div>
      <div className={styles.page}>
        <GradesHeaderButtons
          setSelectedSemester={setSelectedSemester}
          selectedSemester={selectedSemester}
        />

        {isMobile ? (
          <GradesTileContainer
            grades={displayedGrades}
            semesterGrades={displayedSemesterGrades}
            teachingAssignments={teachingAssignments}
            selectedSemester={selectedSemester}
            setSelectedGrade={setSelectedGrade}
            setAssignment={setAssignment}
          />
        ) : (
          <GradesTable
            grades={displayedGrades}
            semesterGrades={displayedSemesterGrades}
            teachingAssignments={teachingAssignments}
            selectedSemester={selectedSemester}
            setSelectedGrade={setSelectedGrade}
            setAssignment={setAssignment}
          />
        )}

        {selectedGrade && (
          <GradeModal
            selectedGrade={selectedGrade}
            assignment={assignment}
            setSelectedGrade={setSelectedGrade}
          />
        )}
      </div>
    </div>
  );
};

export default StudentGrades;
