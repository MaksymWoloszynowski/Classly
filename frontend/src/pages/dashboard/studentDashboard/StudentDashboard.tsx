import { useEffect, useState } from "react";

import type { Schedule } from "../../../types/domain/schedule";
import type { Assessment } from "../../../types/domain/assessment";
import type { Attendance } from "../../../types/domain/attendance";

import api from "../../../api/api";
import useStudentScope from "../../../hooks/useStudentScope";

import Calendar from "@components/calendar/Calendar";
import LoadingOverlay from "@components/loadingOverlay/LoadingOverlay";
import ErrorMessage from "@components/errorMessage/ErrorMessage";
import ScheduleEvent from "@components/schedule/scheduleEvent/ScheduleEvent";

import styles from "../Dashboard.module.css";
import {
  useCalendarController,
  type EventDisplayInfo,
} from "@fullcalendar/react";
import DashboardGrades from "@components/dashboard/dashboardGrades/DashboardGrades";
import type { LatestGradesByTeachingAssignment } from "../../../types";
import DashboardAttendanceItem from "@components/dashboard/dashboardAttendance/DashboardAttendanceItem";
import DashboardAssessmentItem from "@components/dashboard/dashboardAssessment/DashboardAssessmentItem";
import { useTranslation } from "../../../hooks/useTranslation";

const StudentDashboard = () => {
  const { activeStudent } = useStudentScope();
  const { t } = useTranslation();

  const [schedule, setSchedule] = useState<Schedule[]>([]);
  const [grades, setGrades] = useState<LatestGradesByTeachingAssignment | null>(
    null,
  );
  const [assessments, setAssessments] = useState<Assessment[]>([]);
  const [attendance, setAttendance] = useState<Attendance[]>([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const controller = useCalendarController();
  const today = new Date();

  const addDays = (date: Date, days: number) => {
    const result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
  };

  const fetchSchedule = async () => {
    try {
      const date = today.toLocaleDateString("en-CA");

      const response = await api.get<Schedule[]>(
        `/api/schedule/student?groupId=${activeStudent?.groupId}&from=${date}&to=${date}`,
      );

      setSchedule(response.data);
    } catch (error) {
      console.error("Error fetching schedule:", error);
      setError("The schedule could not be loaded.");
    }
  };

  const fetchAssessments = async () => {
    try {
      const from = today.toLocaleDateString("en-CA");
      const to = addDays(today, 7).toLocaleDateString("en-CA");

      const response = await api.get<Assessment[]>(
        `/api/assessment/student/date?groupId=${activeStudent?.groupId}&from=${from}&to=${to}`,
      );

      setAssessments(response.data);
    } catch (error) {
      console.error("Error fetching assessments:", error);
      setError("Assessments could not be loaded.");
    }
  };

  const fetchGrades = async () => {
    try {
      const from = addDays(today, -3).toLocaleDateString("en-CA");
      const to = today.toLocaleDateString("en-CA");

      const response = await api.get<LatestGradesByTeachingAssignment>(
        `/api/grade/date?studentId=${activeStudent?.id}&from=${from}&to=${to}`,
      );

      setGrades(response.data);
    } catch (error) {
      console.error("Error fetching grades:", error);
      setError("Grades could not be loaded.");
    }
  };

  const fetchAttendance = async () => {
    try {
      const from = addDays(today, -2).toLocaleDateString("en-CA");
      const to = today.toLocaleDateString("en-CA");

      const response = await api.get<Attendance[]>(
        `/api/attendance/date?studentId=${activeStudent?.id}&from=${from}&to=${to}`,
      );

      setAttendance(response.data);
    } catch (error) {
      console.error("Error fetching attendance:", error);
      setError("Attendance could not be loaded.");
    }
  };

  useEffect(() => {
    if (!activeStudent) {
      return;
    }

    setLoading(true);
    setError(null);

    Promise.all([
      fetchSchedule(),
      fetchGrades(),
      fetchAssessments(),
      fetchAttendance(),
    ]).finally(() => {
      setLoading(false);
    });
  }, [activeStudent?.id]);

  const events = schedule.map((item) => {
    const override = item.override;

    return {
      title: override?.substituteSubjectName ?? item.subjectName,

      start: `${item.date}T${item.startTime}`,
      end: `${item.date}T${item.endTime}`,

      extendedProps: {
        teacher: override?.substituteTeacherName ?? item.teacherName,
        room: override?.newRoom ?? item.room,
        override,
      },
    };
  });

  const renderEventContent = (eventInfo: EventDisplayInfo) => {
    return <ScheduleEvent eventInfo={eventInfo} />;
  };

  const attendanceIssues = attendance.filter((item) => item.type !== "PRESENT");

  const attendanceByDate = attendanceIssues.reduce<
    Record<string, Attendance[]>
  >((groups, item) => {
    const date = new Date(item.date).toLocaleDateString("pl-PL");

    if (!groups[date]) {
      groups[date] = [];
    }

    groups[date].push(item);

    return groups;
  }, {});

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

        <div className={`${styles.card} ${styles.grades}`}>
          <div className={styles.cardTitle}>{t("grades")}</div>

          {grades ? (
            <DashboardGrades grades={grades} />
          ) : (
            <div className={styles.empty}>{t("gradesLoadError")}</div>
          )}
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

        <div className={`${styles.card} ${styles.attendance}`}>
          <div className={styles.cardTitle}>{t("attendance")}</div>

          {attendance.length > 0 ? (
            <div className={styles.attendanceDays}>
              {Object.entries(attendanceByDate).map(([date, items]) => (
                <div key={date} className={styles.attendanceDay}>
                  <div className={styles.attendanceDate}>{date}</div>

                  <div className={styles.attendanceList}>
                    {items.map((item) => (
                      <DashboardAttendanceItem
                        key={item.id}
                        attendance={item}
                      />
                    ))}
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className={styles.empty}>{t("noAttendanceIssues")}</div>
          )}
        </div>
      </div>
    </>
  );
};

export default StudentDashboard;
