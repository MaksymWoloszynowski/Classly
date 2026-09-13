import { useMediaQuery } from "@mui/material";
import type { Session } from "../../../types/domain/session";
import styles from "./SessionsRow.module.css";
import { formatDate } from "../../../utils/date";

type SessionsRowProps = {
  session: Session;
};

const SessionsRow = ({ session }: SessionsRowProps) => {
  const isMobile = useMediaQuery("(max-width:1000px)");
  const formattedDate = formatDate(new Date(session.date))

  return isMobile ? (
    <div className={styles.tile}>
      <div className={styles.tileTitle}>
        {formattedDate} -{" "}
        {session.subjectName}
      </div>
      <div className={styles.tileContent}>
        <div className={styles.infoRow}>
          <div className={styles.infoColumn}>Teacher</div>
          <div className={styles.infoColumn}>{session.teacherName}</div>
        </div>
        <hr className={styles.line} />
        <div className={styles.infoRow}>
          <div className={styles.infoColumn}>Description</div>
          <div className={styles.infoColumn}>{session.description}</div>
        </div>
      </div>
    </div>
  ) : (
    <div className={styles.sessionsRow}>
      <div className={styles.sessionsCell}>
        {" "}
        {formattedDate}
      </div>
      <div className={styles.sessionsCell}>{session.subjectName}</div>
      <div className={styles.sessionsCell}>{session.teacherName}</div>
      <div className={styles.sessionsCell}>{session.description}</div>
    </div>
  );
};

export default SessionsRow;
