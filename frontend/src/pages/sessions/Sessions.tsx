import { useEffect, useState } from "react";
import type { Session } from "../../types/domain/session";
import useStudentScope from "../../hooks/useStudentScope";
import api from "../../api/api";
import LoadingOverlay from "../../components/loadingOverlay/LoadingOverlay";
import ErrorMessage from "../../components/errorMessage/ErrorMessage";
import styles from "./Sessions.module.css";
import SessionsTable from "@components/sessions/sessionsTable/SessionsTable";
import { ChevronLeft, ChevronRight } from "lucide-react";
import { formatDate } from "../../utils/date";
import { useTranslation } from "../../hooks/useTranslation";

const Sessions = () => {
  const { activeStudent } = useStudentScope();
  const { t } = useTranslation();
  const [sessions, setSessions] = useState<Session[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const getLastMonday = () => {
    const date = new Date();
    const day = date.getDay();
    const daysSinceMonday = (day + 6) % 7;
    date.setDate(date.getDate() - daysSinceMonday);
    return date;
  };

  const [weekStart, setWeekStart] = useState<Date>(getLastMonday());

  const addDays = (date: Date, days: number) => {
    const result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
  };

  const weekEnd = addDays(weekStart, 6);

  const fetchSessions = async () => {
    if (!activeStudent?.groupId) return;

    setLoading(true);
    setError(null);

    try {
      const response = await api.get(
        `/api/session/date?groupId=${activeStudent.groupId}&from=${weekStart.toLocaleDateString("en-CA")}&to=${weekEnd.toLocaleDateString("en-CA")}`,
      );

      setSessions(response.data);
    } catch (error) {
      console.error(error);
      setError("Realized sessions could not be loaded.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSessions();
  }, [weekStart, activeStudent?.groupId]);

  const previousWeek = () => {
    setWeekStart((date) => addDays(date, -7));
  };

  const nextWeek = () => {
    setWeekStart((date) => addDays(date, 7));
  };

  return (
    <>
      {loading && <LoadingOverlay />}
      {error && <ErrorMessage message={error} />}

      <div className={styles.page}>
        <div className={styles.pageTitle}>{t("sessions")}</div>
        <div className={styles.content}>
          <div className={styles.header}>
            <div className={styles.weekPicker}>
              <button className={styles.arrow} onClick={previousWeek}>
                <ChevronLeft />
              </button>

              <span>
                {formatDate(weekStart)} - {formatDate(weekEnd)}
              </span>

              <button className={styles.arrow} onClick={nextWeek}>
                <ChevronRight />
              </button>
            </div>
          </div>

          <SessionsTable sessions={sessions} />
        </div>
      </div>
    </>
  );
};

export default Sessions;
