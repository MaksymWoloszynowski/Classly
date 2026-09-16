import { useTranslation } from "@hooks/useTranslation";
import type { Session } from "../../../types/domain/session";
import SessionsRow from "../sessionsRow/SessionsRow";
import styles from "./SessionsTable.module.css";

type SessionsTableProps = {
  sessions: Session[];
};

const SessionsTable = ({ sessions }: SessionsTableProps) => {
  const {t} = useTranslation();
  
  return (
    <div className={styles.table}>
      <div className={styles.sessionsHeader}>
        <p>{t("date")}</p>
        <p>{t("subject")}</p>
        <p>{(t("teacher"))}</p>
        <p>{t("description")}</p>
      </div>
      {sessions?.length ? (
        sessions.map((session) => (
          <SessionsRow key={session.id} session={session} />
        ))
      ) : (
        <p className={styles.emptyMessage}>{t("noSessions")}</p>
      )}
    </div>
  );
};

export default SessionsTable;
