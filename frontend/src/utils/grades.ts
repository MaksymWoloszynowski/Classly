import type { Grade, GradeType } from "../types/grade";

export const gradeTypeLabels: Record<GradeType, string> = {
  exam: "Sprawdzian",
  homework: "Praca domowa",
  project: "Projekt",
  quiz: "Kartkówka",
  annual: "Ocena roczna",
};

export const getGradeTone = (grade: number) => {
  if (grade >= 5) return "excellent";
  if (grade >= 4) return "good";
  if (grade >= 3) return "okay";
  return "poor";
};

export const getAverage = (grades: Grade[]) => {
  if (!grades.length) return null;

  const totalWeight = grades.reduce((sum, grade) => sum + (grade.weight || 1), 0);
  const weightedSum = grades.reduce(
    (sum, grade) => sum + grade.grade * (grade.weight || 1),
    0,
  );

  return weightedSum / totalWeight;
};

export const formatGradeDate = (date: string) => {
  const parsedDate = new Date(date);

  if (Number.isNaN(parsedDate.getTime())) return date;

  return new Intl.DateTimeFormat("pl-PL", {
    day: "2-digit",
    month: "short",
    year: "numeric",
  }).format(parsedDate);
};
