import { useEffect, useState } from "react";
import { Check } from "lucide-react";
import api from "../../api/api";
import useAuth from "../../hooks/useAuth";
import type { Schedule } from "../../types/domain/schedule";
import type { Session } from "../../types/domain/session";
import LoadingOverlay from "../../components/loadingOverlay/LoadingOverlay";
import SessionRealizationModal from "../../components/sessions/sessionRealizationModal/SessionRealizationModal";
import styles from "./Realizations.module.css";

const today = () => new Date().toLocaleDateString("en-CA");

const Realizations = () => {
  const { auth } = useAuth();
  const [from, setFrom] = useState(today());
  const [to, setTo] = useState(today());
  const [loading, setLoading] = useState(true);
  const [schedule, setSchedule] = useState<Schedule[]>([]);
  const [sessions, setSessions] = useState<Session[]>([]);
  const [showOnlyUnrealized, setShowOnlyUnrealized] = useState(false);
  const [selectedOccurrence, setSelectedOccurrence] = useState<Schedule | null>(
    null,
  );
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        setError(null);
        const [scheduleResponse, sessionResponse] = await Promise.all([
          api.get<Schedule[]>(
            `/api/schedule/teacher?teacherId=${auth?.teacher?.id}&from=${from}&to=${to}`,
          ),
          api.get<Session[]>(
            `/api/session/query/teacher?from=${from}&to=${to}`,
          ),
        ]);
        setSchedule(scheduleResponse.data);
        setSessions(sessionResponse.data);
      } catch {
        setError("Error fetching data.");
      } finally {
        setLoading(false);
      }
    };
    load();
  }, [from, to, auth?.teacher?.id]);

  const sessionFor = (item: Schedule) =>
    sessions.find(
      (session) =>
        session.scheduleId === item.scheduleId && session.date === item.date,
    );

  const visibleSchedule = schedule.filter(
    (item) => !showOnlyUnrealized || !sessionFor(item),
  );
  const dates = [...new Set(visibleSchedule.map((item) => item.date))];

  const selectOccurrence = (item: Schedule) => {
    if (item.override?.type !== "CANCELLED") {
      setSelectedOccurrence(item);
    }
  };

  const handleSessionSaved = (updated: Session) => {
    setSessions((current) => {
      const index = current.findIndex((session) => session.id === updated.id);
      if (index === -1) return [...current, updated];
      return current.map((session) =>
        session.id === updated.id ? updated : session,
      );
    });
  };

  const selectedSession = selectedOccurrence
    ? sessionFor(selectedOccurrence)
    : undefined;

  return (
    <>
      {loading && <LoadingOverlay />}
      <div className={styles.page}>
        <div className={styles.pageTitle}>Realizations</div>
        <div className={styles.filters}>
          <input
            type="date"
            value={from}
            onChange={(event) => setFrom(event.target.value)}
            max={to || undefined}
          />
          <span>–</span>
          <input
            type="date"
            value={to}
            onChange={(event) => setTo(event.target.value)}
            min={from || undefined}
          />
          <label className={styles.toggle}>
            <input
              type="checkbox"
              checked={showOnlyUnrealized}
              onChange={(event) => setShowOnlyUnrealized(event.target.checked)}
            />
            Show only unrealized
          </label>
        </div>
        {error && <p className={styles.error}>{error}</p>}
        <section className={styles.list}>
          {dates.length === 0 && <p>No sessions found.</p>}
          {dates.map((date) => (
            <div key={date} className={styles.listItem}>
              <div className={styles.date}>{date}</div>
              {visibleSchedule
                .filter((item) => item.date === date)
                .map((item) => {
                  const session = sessionFor(item);
                  return (
                    <button
                      type="button"
                      key={`${item.scheduleId}-${item.date}`}
                      className={`${styles.item} ${selectedOccurrence === item ? styles.selected : ""}`}
                      onClick={() => selectOccurrence(item)}
                      disabled={item.override?.type === "CANCELLED"}
                    >
                      <span>
                        {item.startTime.slice(0, 5)}–{item.endTime.slice(0, 5)}
                      </span>
                      <span>{item.subjectName}</span>
                      <span>Group: {item.groupName}</span>
                      {session && (
                        <Check
                          className={styles.check}
                          aria-label="Realized"
                        />
                      )}
                    </button>
                  );
                })}
            </div>
          ))}
        </section>
      </div>
      {selectedOccurrence && (
        <SessionRealizationModal
          occurrence={selectedOccurrence}
          session={selectedSession}
          onClose={() => setSelectedOccurrence(null)}
          onSaved={handleSessionSaved}
        />
      )}
    </>
  );
};

export default Realizations;
