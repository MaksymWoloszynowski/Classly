import { useEffect, useState } from "react";
import type { Session } from "../../types/session";
import useAuth from "../../hooks/useAuth";
import api from "../../api/api";
import LoadingOverlay from "../../components/loadingOverlay/LoadingOverlay";

const Sessions = () => {
  const { auth } = useAuth();
  const [sessions, setSessions] = useState<Session[] | null>(null);
  const [loading, setLoading] = useState(true);
  const [weekStart, setWeekStart] = useState(new Date());

  const addDays = (date: Date, days: number) => {
    const result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
  };

  const formatDate = (date: Date) =>
    date.toLocaleDateString("pl-PL", {
      day: "2-digit",
      month: "2-digit",
    });

  const weekEnd = addDays(weekStart, 6);

  const fetchSessions = async () => {
    if (!auth?.student?.groupId) return;

    setLoading(true);

    try {
      const response = await api.get(
        `/api/session/date?groupId=${auth.student.groupId}&from=${weekStart.toLocaleDateString("en-CA")}&to=${weekEnd.toLocaleDateString("en-CA")}`,
      );

      setSessions(response.data);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSessions();
  }, [weekStart, auth?.student?.groupId]);

  const previousWeek = () => {
    setWeekStart((date) => addDays(date, -7));
  };

  const nextWeek = () => {
    setWeekStart((date) => addDays(date, 7));
  };

  return (
    <>
      {loading && <LoadingOverlay />}

      <div>
        <div className="week-picker">
          <button onClick={previousWeek}>←</button>

          <span>
            {formatDate(weekStart)} - {formatDate(weekEnd)}
          </span>

          <button onClick={nextWeek}>→</button>
        </div>

        <div>
          {sessions?.map((session) => (
            <div key={session.id}>{session.description}</div>
          ))}
        </div>
      </div>
    </>
  );
};

export default Sessions;
