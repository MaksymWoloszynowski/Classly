import { useEffect, useState } from "react";
import api from "../../api/api";
import "./Grades.css";
import GradesTable from "../../components/grades/gradesTable/GradesTable";
import type { Grade } from "../../types/grade";
import type { Subject } from "../../types/subject";
import LoadingOverlay from "../../components/loadingOverlay/LoadingOverlay";
import useAuth from "../../hooks/useAuth";
import type { ClassificationPeriod } from "../../types/classificationPeriod";

const Grades = () => {
  const [firstSemesterGrades, setFirstSemesterGrades] = useState<Grade[]>([]);
  const [secondSemesterGrades, setSecondSemesterGrades] = useState<Grade[]>([]);
  const [annualGrades, setAnnualGrades] = useState<Grade[]>([]);
  const [classificationPeriods, setClassificationPeriods] = useState<
    ClassificationPeriod[]
  >([]);
  const [subjects, setSubjects] = useState<Subject[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const { auth } = useAuth();

  const fetchClassificationPeriods = async () => {
    try {
      const response = await api.get<ClassificationPeriod[]>(
        "/api/classification-period",
      );

      setClassificationPeriods(response.data);

      return response.data;
    } catch (error) {
      console.error("Error fetching classification periods:", error);
      return [];
    }
  };

  const fetchGrades = async (periods: ClassificationPeriod[]) => {
    try {
      const semesterGradesResult = await api.get<Grade[]>(
        "/api/grade?studentId=" + auth?.student?.id,
      );

      const annualGradesResult = await api.get<Grade[]>(
        "/api/semester-grade?studentId=" + auth?.student?.id,
      );

      const semesterGrades = semesterGradesResult.data;

      setFirstSemesterGrades(
        semesterGrades.filter(
          (grade) =>
            grade.classificationPeriod === periods[0]?.id,
        ),
      );

      setSecondSemesterGrades(
        semesterGrades.filter(
          (grade) =>
            grade.classificationPeriod === periods[1]?.id,
        ),
      );

      // setAnnualGrades(
        // annualGradesResult.data.filter((grade) => grade.type === "annual"),
      // );
    } catch (error) {
      console.error("Error fetching grades:", error);
    }
  };

  const fetchSubjects = async () => {
    try {
      const subjectsResponse = await api.get<Subject[]>("/api/subject");
      setSubjects(subjectsResponse.data);
    } catch (error) {
      console.error("Error fetching subjects:", error);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const periods = await fetchClassificationPeriods();

        await Promise.all([
          fetchGrades(periods),
          fetchSubjects(),
        ]);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  return (
    <main className="grades-page">
      {isLoading && <LoadingOverlay />}

      <GradesTable
        firstSemesterGrades={firstSemesterGrades}
        secondSemesterGrades={secondSemesterGrades}
        annualGrades={annualGrades}
        subjects={subjects}
      />
    </main>
  );
};

export default Grades;