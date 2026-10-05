import type { AssessmentType } from "@types-local/enums";
import styles from "../Assessments.module.css";
import type { Assessment } from "@types-local/index";
import { useEffect, useState } from "react";
import {
  useCalendarController,
  type EventClickInfo,
  type EventDisplayInfo,
} from "@fullcalendar/react";
import useAuth from "@hooks/useAuth";
import api from "@api/api";
import LoadingOverlay from "@components/loadingOverlay/LoadingOverlay";
import ErrorMessage from "@components/errorMessage/ErrorMessage";
import Calendar from "@components/calendar/Calendar";
import AssessmentModal from "@components/assessment/assessmentModal/AssessmentModal";
import AssessmentCreateModal from "@components/assessment/assessmentCreateModal/AssessmentCreateModal";
import { Plus, X } from "lucide-react";
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

const TeacherAssessments = () => {
  const [assessments, setAssessments] = useState<Assessment[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const [selectedAssessment, setSelectedAssessment] =
    useState<Assessment | null>(null);

  const [assessmentToEdit, setAssessmentToEdit] = useState<Assessment | null>(
    null,
  );

  const [selectedAssignmentId, setSelectedAssignmentId] = useState("");
  const [selectedDate, setSelectedDate] = useState<string | null>(null);

  const [addAssessment, setAddAssessment] = useState(false);

  const controller = useCalendarController();
  const { auth } = useAuth();
  const { t } = useTranslation();
  const subjectLabels = useSubjectLabels()
  const assessmentTypeLabels = useAssessmentTypeLabels()

  const assignments = auth?.teacher?.teachingAssignments ?? [];
  const activeAssignmentId = selectedAssignmentId || assignments[0]?.id || "";
  const selectedAssignment = assignments.find(
    (assignment) => assignment.id === activeAssignmentId,
  );

  const editAssignment = assessmentToEdit
    ? assignments.find(
        (assignment) => assignment.id === assessmentToEdit.teachingAssignmentId,
      )
    : undefined;

  const fetchAssessments = async (start?: Date, end?: Date) => {
    const viewStart = start ?? controller.view?.activeStart;
    const viewEnd = end ?? controller.view?.activeEnd;

    if (!viewStart || !viewEnd) return;

    const startFormat = viewStart.toLocaleDateString("en-CA");
    const endFormat = viewEnd.toLocaleDateString("en-CA");

    try {
      setLoading(true);
      setError(null);

      const endpoint = addAssessment
        ? `/api/assessment/student/date?groupId=${selectedAssignment?.groupId}&from=${startFormat}&to=${endFormat}`
        : `/api/assessment/teacher/date?from=${startFormat}&to=${endFormat}`;

      const response = await api.get<Assessment[]>(endpoint);

      setAssessments(response.data);
    } catch (error) {
      console.error("Error fetching assessments:", error);
      setError("Assessments could not be loaded.");
    } finally {
      setLoading(false);
    }
  };

  const handleEventClick = (info: EventClickInfo) => {
    const assessment = assessments.find((item) => item.id === info.event.id);

    if (assessment) {
      setSelectedAssessment(assessment);
    }
  };

  const events =
    assessments.map((item: Assessment) => ({
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

    return (
      <div className={`${styles.event} ${typeClass[type]}`}>
        <span className={styles.type}>{assessmentTypeLabels[type]}</span>

        <span className={styles.subject}>{subjectLabels[formatSubject(subject)]}</span>
      </div>
    );
  };

  const handleDeleteAssessment = async () => {
    if (!selectedAssessment) return;

    try {
      setLoading(true);

      await api.delete(`/api/assessment/${selectedAssessment.id}`);

      setAssessments((current) =>
        current.filter((item) => item.id !== selectedAssessment.id),
      );

      setSelectedAssessment(null);
    } catch (error) {
      console.error("Error deleting assessment:", error);
      setError("The assessment could not be deleted.");
    } finally {
      setLoading(false);
    }
  };

  const handleAssessmentSaved = (assessment: Assessment) => {
    setAssessments((current) => {
      const exists = current.some((item) => item.id === assessment.id);

      if (exists) {
        return current.map((item) =>
          item.id === assessment.id ? assessment : item,
        );
      }

      return [...current, assessment];
    });
  };

  useEffect(() => {
    fetchAssessments();
  }, [selectedAssignmentId, addAssessment]);

  return (
    <>
      {loading && <LoadingOverlay />}
      {error && <ErrorMessage message={error} />}

      <div className={styles.page}>
        <div className={styles.pageTitle}>{t("assessments")}</div>

        <div className={styles.assessmentControls}>
          {!addAssessment ? (
            <button
              type="button"
              className={styles.add}
              onClick={() => {
                setAddAssessment(true);
              }}
            >
              <Plus />
              {t("addAssessment")}
            </button>
          ) : (
            <>
              <label className={styles.assignmentPicker}>
                <span>{t("subjectAndGroup")}</span>

                <select
                  value={activeAssignmentId}
                  onChange={(event) => {
                    setSelectedAssignmentId(event.target.value);
                    setSelectedDate(null);
                  }}
                  disabled={assignments.length === 0}
                >
                  {assignments.map((assignment) => (
                    <option key={assignment.id} value={assignment.id}>
                      {assignment.subjectName} · {t("group")} {assignment.groupName}
                    </option>
                  ))}
                </select>
              </label>
              <div className={styles.addMode}>
                <button
                  type="button"
                  className={styles.closeAddButton}
                  onClick={() => {
                    setAddAssessment(false);
                    setSelectedDate(null);
                  }}
                  aria-label={t("closeAddAssessmentMode")}
                >
                  <span>{t("cancel")}</span>
                  <X size={18} />
                </button>
              </div>
            </>
          )}
        </div>

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
            handleDateClick={
              selectedAssignment && addAssessment
                ? (info) => {
                    setSelectedDate(info.dateStr);
                  }
                : undefined
            }
          />

          {selectedAssessment && (
            <AssessmentModal
              selectedAssessment={selectedAssessment}
              setSelectedAssessment={setSelectedAssessment}
              onDelete={handleDeleteAssessment}
              onUpdate={() => {
                setAssessmentToEdit(selectedAssessment);
                setSelectedAssessment(null);
              }}
            />
          )}

          {selectedDate && selectedAssignment && (
            <AssessmentCreateModal
              date={selectedDate}
              assignment={selectedAssignment}
              onClose={() => {
                setSelectedDate(null);
              }}
              onSaved={handleAssessmentSaved}
            />
          )}

          {assessmentToEdit && editAssignment && (
            <AssessmentCreateModal
              date={assessmentToEdit.dateDue.toString()}
              assignment={editAssignment}
              assessment={assessmentToEdit}
              onClose={() => {
                setAssessmentToEdit(null);
              }}
              onSaved={(updatedAssessment) => {
                handleAssessmentSaved(updatedAssessment);
                setAssessmentToEdit(null);
              }}
            />
          )}
        </div>
      </div>
    </>
  );
};

export default TeacherAssessments;
