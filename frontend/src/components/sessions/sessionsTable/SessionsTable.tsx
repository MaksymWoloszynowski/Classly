import type { Session } from "../../../types/domain/session";
import SessionsRow from "../sessionsRow/SessionsRow";
import styles from "./SessionsTable.module.css";

type SessionsTableProps = {
  sessions: Session[];
};

const SessionsTable = ({ sessions }: SessionsTableProps) => {
  return (
    <div className={styles.table}>
      <div className={styles.sessionsHeader}>
        <p>Date</p>
        <p>Subject</p>
        <p>Teacher</p>
        <p>Description</p>
      </div>
      {sessions?.length ? (
        sessions.map((session) => (
          <SessionsRow key={session.id} session={session} />
        ))
      ) : (
        <p className={styles.emptyMessage}>No sessions</p>
      )}
    </div>
  );
};

export default SessionsTable;
