import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { ArrowRight, CircleAlert } from "lucide-react";
import api from "../../../api/api";
import useAuth from "../../../hooks/useAuth";
import type { Assessment } from "../../../types/domain/assessment";
import type { Schedule } from "../../../types/domain/schedule";
import type { Session } from "../../../types/domain/session";
import LoadingOverlay from "../../../components/loadingOverlay/LoadingOverlay";
import ErrorMessage from "../../../components/errorMessage/ErrorMessage";
import styles from "../Dashboard.module.css";
import {
  useCalendarController,
  type EventDisplayInfo,
} from "@fullcalendar/react";
import Calendar from "@components/calendar/Calendar";
import ScheduleEvent from "@components/schedule/scheduleEvent/ScheduleEvent";
import DashboardAssessmentItem from "@components/dashboard/dashboardAssessment/DashboardAssessmentItem";
import useLocalePath from "../../../hooks/useLocalePath";
import { useTranslation } from "../../../hooks/useTranslation";

const formatDate = (date: Date) => date.toLocaleDateString("en-CA");

const addDays = (date: Date, days: number) => {
  const result = new Date(date);
  result.setDate(result.getDate() + days);
  return result;
};

const TeacherDashboard = () => {
  const { auth } = useAuth();
  const [schedule, setSchedule] = useState<Schedule[]>([]);
  const [assessments, setAssessments] = useState<Assessment[]>([]);
  const [sessions, setSessions] = useState<Session[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const controller = useCalendarController();
  const localePath = useLocalePath();
  const { t } = useTranslation();

  const today = new Date();
  const todayDate = formatDate(today);
  const weekEnd = formatDate(addDays(today, 7));

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
      },
    };
  });

  const renderEventContent = (eventInfo: EventDisplayInfo) => {
    return <ScheduleEvent eventInfo={eventInfo} />;
  };

  useEffect(() => {
    const teacherId = auth?.teacher?.id;
    if (!teacherId) return;

    const loadDashboard = async () => {
      try {
        setLoading(true);
        setError(null);
        const [scheduleResponse, assessmentResponse, sessionResponse] =
          await Promise.all([
            api.get<Schedule[]>(
              `/api/schedule/teacher?teacherId=${teacherId}&from=${todayDate}&to=${todayDate}`,
            ),
            api.get<Assessment[]>(
              `/api/assessment/teacher/date?from=${todayDate}&to=${weekEnd}`,
            ),
            api.get<Session[]>(
              `/api/session/query/teacher?from=${todayDate}&to=${weekEnd}`,
            ),
          ]);

        setSchedule(scheduleResponse.data);
        setAssessments(
          assessmentResponse.data.sort(
            (first, second) =>
              new Date(first.dateDue).getTime() -
              new Date(second.dateDue).getTime(),
          ),
        );
        setSessions(sessionResponse.data);
      } catch (error) {
        console.error("Error fetching teacher dashboard:", error);
        setError("Dashboard data could not be loaded.");
      } finally {
        setLoading(false);
      }
    };

    loadDashboard();
  }, [auth?.teacher?.id, todayDate, weekEnd]);

  const unrealizedCount = schedule.filter(
    (item) =>
      item.override?.type !== "CANCELLED" &&
      item.scheduleId &&
      !sessions.some(
        (session) =>
          session.scheduleId === item.scheduleId && session.date === item.date,
      ),
  ).length;

  return (
    <>
      {loading && <LoadingOverlay />}
      {error && <ErrorMessage message={error} />}
      <div className={styles.pageTitle}>{t("dashboard")}</div>

      <div className={styles.dashboard}>
        <div className={`${styles.card} ${styles.schedule}`}>
          <div className={styles.cardTitle}>{t("schedule")}</div>
          <Calendar
            controller={controller}
            events={events}
            action={() => {}}
            renderEventContent={renderEventContent}
            initialView="timeGridDay"
            headerToolbar={{
              center: "title",
            }}
          />
        </div>

        <div className={`${styles.card} ${styles.assessments}`}>
          <div className={styles.cardTitle}>{t("assessments")}</div>

          {assessments.length > 0 ? (
            <div className={styles.assessmentList}>
              {assessments.map((assessment) => (
                <DashboardAssessmentItem
                  key={assessment.id}
                  assessment={assessment}
                />
              ))}
            </div>
          ) : (
            <div className={styles.empty}>{t("noUpcomingAssessments")}</div>
          )}
        </div>

        <div className={`${styles.card} ${styles.realizations}`}>
          <div className={styles.cardTitle}>{t("realizations")}</div>
          {unrealizedCount ? (
            <div>
              <div className={styles.alert}>
                  <div className={styles.alertIcon}>
                    <CircleAlert />
                  </div>
                  <div>{t("sessionsToRealize")}</div>
              </div>
              <Link className={styles.secondaryLink} to={localePath("/teacher/realizations")}>
                {t("goToRealization")} <ArrowRight size={17} />
              </Link>
            </div>
          ) : (
            <div>{t("noSessionsToRealize")}</div>
          )}
        </div>
      </div>
    </>
  );
};

export default TeacherDashboard;
