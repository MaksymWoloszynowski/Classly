import { useEffect, useState } from "react";
import api from "../../api/api";
import "./Grades.css";
import GradesTable from "../../components/grades/gradesTable/GradesTable";
import type { Grade } from "../../types/grade";
import type { Subject } from "../../types/subject";
import LoadingOverlay from "../../components/loadingOverlay/LoadingOverlay";

const Grades = () => {
    const [firstSemesterGrades, setFirstSemesterGrades] = useState<Grade[]>([]);
    const [secondSemesterGrades, setSecondSemesterGrades] = useState<Grade[]>([]);
    const [annualGrades, setAnnualGrades] = useState<Grade[]>([]);
    const [subjects, setSubjects] = useState<Subject[]>([]);
    const [isLoading, setIsLoading] = useState(true);

    const fetchGrades = async () => {
        try {
            const semesterGradesResult = await api.get<Grade[]>("/grade");
            const annualGradesResult = await api.get<Grade[]>("/semester-grade");
            const semesterGrades = semesterGradesResult.data;

            setFirstSemesterGrades(semesterGrades.filter((grade) => grade.semester === 1));
            setSecondSemesterGrades(semesterGrades.filter((grade) => grade.semester === 2));
            setAnnualGrades(annualGradesResult.data.filter((grade) => grade.type === "annual"));
        } catch (error) {
            console.error("Error fetching grades:", error);
        }
    };

    const fetchSubjects = async () => {
        try {
            const subjectsResponse = await api.get<Subject[]>("/subject");
            setSubjects(subjectsResponse.data);
        } catch (error) {
            console.error("Error fetching subjects:", error);
        }
    };

    useEffect(() => {
        Promise.all([fetchGrades(), fetchSubjects()]).finally(() => setIsLoading(false));
    }, []);

    return (
        <main className="grades-page">
            {isLoading && <LoadingOverlay />}
            <GradesTable 
                firstSemesterGrades={firstSemesterGrades} 
                secondSemesterGrades={secondSemesterGrades} 
                annualGrades={annualGrades} 
                subjects={subjects}
                isLoading={isLoading}
            />
        </main>
    );
};

export default Grades;
