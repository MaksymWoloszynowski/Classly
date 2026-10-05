import type { AssessmentType } from "@types-local/enums";
import styles from "../Assessments.module.css";
import type { Assessment } from "@types-local/index";
import { useState } from "react";
import {
  useCalendarController,
  type EventClickInfo,
  type EventDisplayInfo,
} from "@fullcalendar/react";
import useStudentScope from "@hooks/useStudentScope";
import api from "@api/api";
import LoadingOverlay from "@components/loadingOverlay/LoadingOverlay";
import ErrorMessage from "@components/errorMessage/ErrorMessage";
import Calendar from "@components/calendar/Calendar";
import AssessmentModal from "@components/assessment/assessmentModal/AssessmentModal";
import { useTranslation } from "../../../hooks/useTranslation";
import { useAssessmentTypeLabels, useSubjectLabels } from "@types-local/labels";
import { formatSubject } from "@utils/subject";

const typeClass: Record<AssessmentType, string> = {
  TEST: styles.test,
  QUIZ: styles.quiz,
  CLASS_TEST: styles.classTest,
  HOMEWORK: styles.homework,
};

type AssessmentEventProps = {
  subject: string;
  teacher: string;
  description: string;
  type: AssessmentType;
};

const StudentAssessments = () => {
  const [assessments, setAssessments] = useState<Assessment[] | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedAssessment, setSelectedAssessment] =
    useState<Assessment | null>(null);

  const controller = useCalendarController();
  const { activeStudent } = useStudentScope();
  const { t } = useTranslation();
  const subjectLabels = useSubjectLabels()
  const assessmentTypeLabels = useAssessmentTypeLabels()

  const fetchAssessments = async (start?: Date, end?: Date) => {
    const viewStart = start ?? controller.view?.activeStart;
    const viewEnd = end ?? controller.view?.activeEnd;
    if (!viewStart || !viewEnd) return;

    try {
      setError(null);
      const response = await api.get<Assessment[]>(
        `/api/assessment/student/date?groupId=${activeStudent?.groupId}&from=${viewStart.toLocaleDateString("en-CA")}&to=${viewEnd.toLocaleDateString("en-CA")}`,
      );
      setAssessments(response.data);
    } catch (error) {
      console.error("Error fetching assessments:", error);
      setError("Assessments could not be loaded.");
    } finally {
      setLoading(false);
    }
  };

  const handleEventClick = (info: EventClickInfo) => {
    const assessment = assessments?.find((item) => item.id === info.event.id);

    if (assessment) {
      setSelectedAssessment(assessment);
    }
  };

  const events =
    assessments?.map((item: Assessment) => ({
      id: item.id,
      title: item.type,
      start: item.dateDue,
      allDay: true,
      extendedProps: {
        subject: item.subjectName,
        teacher: item.teacherName,
        description: item.description,
        type: item.type,
      },
    })) ?? [];

  const renderEventContent = (eventInfo: EventDisplayInfo) => {
    const { subject, type } = eventInfo.event
      .extendedProps as AssessmentEventProps;
      
      console.log
    return (
      <div className={`${styles.event} ${typeClass[type]}`}>
        <span className={styles.type}>{assessmentTypeLabels[type]}</span>
        <span className={styles.subject}>{subjectLabels[formatSubject(subject)]}</span>
      </div>
    );
  };
  return (
    <>
      {loading && <LoadingOverlay />}
      {error && <ErrorMessage message={error} />}
      <div className={styles.page}>
        <div className={styles.pageTitle}>{t("assessments")}</div>
        <div className={styles.calendar}>
          <Calendar
            controller={controller}
            action={fetchAssessments}
            events={events}
            renderEventContent={renderEventContent}
            initialView="dayGridMonth"
            handleEventClick={handleEventClick}
            headerToolbar={{
              left: "prev,next",
              center: "title",
            }}
          />
          {selectedAssessment && (
            <AssessmentModal
              selectedAssessment={selectedAssessment}
              setSelectedAssessment={setSelectedAssessment}
            />
          )}
        </div>
      </div>
    </>
  );
};

export default StudentAssessments;
