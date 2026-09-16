import { useEffect, useState } from "react";
import type { Attendance } from "../../types/domain/attendance";
import {
  useCalendarController,
  type EventDisplayInfo,
} from "@fullcalendar/react";
import Calendar from "../../components/calendar/Calendar";
import LoadingOverlay from "../../components/loadingOverlay/LoadingOverlay";
import ErrorMessage from "../../components/errorMessage/ErrorMessage";
import useStudentScope from "../../hooks/useStudentScope";
import api from "../../api/api";
import styles from "./Attendance.module.css";
import { useTranslation } from "../../hooks/useTranslation";

const AttendancePage = () => {
  const { activeStudent } = useStudentScope();
  const { t } = useTranslation();
  const [attendance, setAttendance] = useState<Attendance[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [viewType, setViewType] = useState("timeGridWeek");
  const controller = useCalendarController();

  const fetchAttendance = async (start: Date, end: Date) => {
    try {
      setError(null);
      const response = await api.get(
        `/api/attendance/date?studentId=${activeStudent?.id}&from=${start.toLocaleDateString("en-CA")}&to=${end.toLocaleDateString("en-CA")}`,
      );

      setAttendance(response.data);
    } catch (error) {
      console.error("Error fetching attendance:", error);
      setError("Attendance could not be loaded.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (!activeStudent?.id || !controller.view) return;

    const loadAttendance = async () => {
      await fetchAttendance(
        controller.view!.activeStart,
        controller.view!.activeEnd,
      );
    };

    loadAttendance();
  }, [activeStudent?.id, controller.view]);

  const events =
    attendance.map((item: Attendance) => ({
      title: item.subject,
      start: `${item.date}T${item.startTime}`,
      end: `${item.date}T${item.endTime}`,
      extendedProps: {
        teacher: item.teacher,
        type: item.type.toLowerCase(),
      },
    })) ?? [];

  const present = attendance.filter((item) => item.type === "PRESENT").length;
  const absent = attendance.filter((item) => item.type === "ABSENT").length;
  const unexcused = attendance.filter(
    (item) => item.type === "UNEXCUSED_ABSENCE",
  ).length;
  const tardy = attendance.filter((item) => item.type === "TARDY").length;

  const summaryHeaderText =
    viewType === "dayGridMonth" ? t("monthSummary") : t("weekSummary");

  const renderEventContent = (eventInfo: EventDisplayInfo) => {
    return (
      <div
        className={`${styles.event} ${styles[eventInfo.event.extendedProps.type]}`}
      >
        <div className={styles.time}>
          {eventInfo.event.start?.toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit",
          })}{" "}
          -{" "}
          {eventInfo.event.end?.toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit",
          })}
        </div>
        <div>
          <div className={styles.subject}>{eventInfo.event.title}</div>

          <div className={styles.teacher}>
            {eventInfo.event.extendedProps.teacher}
          </div>
        </div>
      </div>
    );
  };

  return (
    <>
      {loading && <LoadingOverlay />}
      {error && <ErrorMessage message={error} />}
      <div className={styles.page}>
        <div className={styles.pageTitle}>{t("attendance")}</div>
        <div className={styles.calendar}>
          <Calendar
            controller={controller}
            events={events}
            action={fetchAttendance}
            renderEventContent={renderEventContent}
            initialView={viewType}
            headerToolbar={{
              left: "prev,next",
              center: "title",
              right: "timeGridWeek,dayGridMonth",
            }}
            setViewType={setViewType}
          />
        </div>

        <div className={styles.container}>
          <div className={styles.header}>{summaryHeaderText}</div>
          <div className={styles.summary}>
            <div className={`${styles.summaryBox} ${styles.present}`}>
              <div>{t("present")}</div>
              <div className={styles.summaryNumber}>{present}</div>
            </div>
            <div className={`${styles.summaryBox} ${styles.absent}`}>
              <div>{t("absent")}</div>
              <div className={styles.summaryNumber}>{absent}</div>
            </div>
            <div className={`${styles.summaryBox} ${styles.unexcusedAbsence}`}>
              <div>{t("unexcusedAbsence")}</div>
              <div className={styles.summaryNumber}>{unexcused}</div>
            </div>
            <div className={`${styles.summaryBox} ${styles.tardy}`}>
              <div>{t("tardy")}</div>
              <div className={styles.summaryNumber}>{tardy}</div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default AttendancePage;
