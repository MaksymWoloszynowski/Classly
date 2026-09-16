import {
  useCalendarController,
  type EventClickInfo,
  type EventDisplayInfo,
} from "@fullcalendar/react";
import api from "../../api/api";
import Calendar from "../../components/calendar/Calendar";
import { useEffect, useState } from "react";
import useAuth from "../../hooks/useAuth";
import useStudentScope from "../../hooks/useStudentScope";
import type { Schedule } from "../../types/domain/schedule";
import LoadingOverlay from "../../components/loadingOverlay/LoadingOverlay";
import ErrorMessage from "../../components/errorMessage/ErrorMessage";
import styles from "./Schedule.module.css";
import ScheduleEvent from "@components/schedule/scheduleEvent/ScheduleEvent";
import SessionRealizationModal from "@components/sessions/sessionRealizationModal/SessionRealizationModal";
import type { Session } from "../../types/domain/session";
import { useTranslation } from "../../hooks/useTranslation";

const SchedulePage = () => {
  const [schedule, setSchedule] = useState<Schedule[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [sessions, setSessions] = useState<Session[]>([]);
  const [selectedOccurrence, setSelectedOccurrence] = useState<Schedule | null>(null);

  const controller = useCalendarController();
  const { auth } = useAuth();
  const { activeStudent } = useStudentScope();
  const { t } = useTranslation();

  const fetchSchedule = async (start?: Date, end?: Date) => {
    const viewStart = start ?? controller.view?.activeStart;
    const viewEnd = end ?? controller.view?.activeEnd;

    if (!viewStart || !viewEnd) return;

    setLoading(true);
    setError(null);
    try {
      const from = viewStart.toLocaleDateString("en-CA");
      const to = viewEnd.toLocaleDateString("en-CA");
      const isTeacher = auth?.role === "ROLE_TEACHER";

      const scheduleEndpoint = isTeacher
        ? `/api/schedule/teacher?teacherId=${auth?.teacher?.id}&from=${from}&to=${to}`
        : `/api/schedule/student?groupId=${activeStudent?.groupId}&from=${from}&to=${to}`;

      const scheduleRequest = api.get<Schedule[]>(scheduleEndpoint);
      const sessionsRequest = isTeacher
        ? api.get<Session[]>(`/api/session?from=${from}&to=${to}`)
        : Promise.resolve({ data: [] as Session[] });

      const [scheduleResponse, sessionsResponse] = await Promise.all([scheduleRequest, sessionsRequest]);
      setSchedule(scheduleResponse.data);
      setSessions(sessionsResponse.data);
    } catch (error) {
      console.error("Error fetching schedule:", error);
      setError("The schedule could not be loaded.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSchedule();
  }, [activeStudent?.groupId, auth?.role, auth?.teacher?.teachingAssignments]);

  const events = schedule.map((item) => {
    const override = item.override;

    return {
      title: override?.substituteSubjectName ?? item.subjectName,
      start: `${item.date}T${item.startTime}`,
      end: `${item.date}T${item.endTime}`,
      extendedProps: {
        teacher: override?.substituteTeacherName ?? item.teacherName,
        room: override?.newRoom ?? item.room,
        group: item.groupName,
        override,
        realized: Boolean(item.scheduleId && sessions.some(
          (session) => session.scheduleId === item.scheduleId && session.date === item.date,
        )),
        occurrence: item,
      },
    };
  });

  const renderEventContent = (eventInfo: EventDisplayInfo) => {
    return <ScheduleEvent eventInfo={eventInfo} />;
  };

  const handleEventClick = (info: EventClickInfo) => {
    const { occurrence } = info.event.extendedProps as {
      occurrence: Schedule;
      realized: boolean;
    };
    const cancelled = occurrence.override?.type === "CANCELLED";
    if (auth?.role === "ROLE_TEACHER" && occurrence.scheduleId && !cancelled) {
      setSelectedOccurrence(occurrence);
    }
  };

  const handleSessionSaved = (session: Session) => {
    setSessions((current) => {
      const exists = current.some((item) => item.id === session.id);
      return exists
        ? current.map((item) => item.id === session.id ? session : item)
        : [...current, session];
    });
  };

  return (
    <>
      {loading && <LoadingOverlay />}
      {error && <ErrorMessage message={error} />}
      <div className={styles.page}>
        <div className={styles.pageTitle}>{t("schedule")}</div>
        <div className={styles.calendar}>
        <Calendar
          controller={controller}
          action={fetchSchedule}
          events={events}
          renderEventContent={renderEventContent}
          handleEventClick={handleEventClick}
          initialView="timeGridWeek"
          headerToolbar={{
            left: "prev,next",
            center: "title",
            right: "timeGridWeek,dayGridMonth",
          }}
        />
        </div>
      </div>
      {selectedOccurrence && (
        <SessionRealizationModal
          occurrence={selectedOccurrence}
          session={sessions.find(
            (item) => item.scheduleId === selectedOccurrence.scheduleId && item.date === selectedOccurrence.date,
          )}
          onClose={() => setSelectedOccurrence(null)}
          onSaved={handleSessionSaved}
        />
      )}
    </>
  );
};

export default SchedulePage;
