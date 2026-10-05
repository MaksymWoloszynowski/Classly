import { useMediaQuery } from "@mui/material";
import type { Session } from "../../../types/domain/session";
import styles from "./SessionsRow.module.css";
import { formatDate } from "../../../utils/date";
import { useTranslation } from "@hooks/useTranslation";
import { useSubjectLabels } from "@types-local/labels";
import { formatSubject } from "@utils/subject";

type SessionsRowProps = {
  session: Session;
};

const SessionsRow = ({ session }: SessionsRowProps) => {
  const isMobile = useMediaQuery("(max-width:1000px)");
  const formattedDate = formatDate(new Date(session.date))
  const {t} = useTranslation()
  const subjectLabels = useSubjectLabels()

  return isMobile ? (
    <div className={styles.tile}>
      <div className={styles.tileTitle}>
        {formattedDate} -{" "}
        {subjectLabels[formatSubject(session.subjectName)]}
      </div>
      <div className={styles.tileContent}>
        <div className={styles.infoRow}>
          <div className={styles.infoColumn}>{t("teacher")}</div>
          <div className={styles.infoColumn}>{session.teacherName}</div>
        </div>
        <hr className={styles.line} />
        <div className={styles.infoRow}>
          <div className={styles.infoColumn}>{t("description")}</div>
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
      <div className={styles.sessionsCell}>{subjectLabels[formatSubject(session.subjectName)]}</div>
      <div className={styles.sessionsCell}>{session.teacherName}</div>
      <div className={styles.sessionsCell}>{session.description}</div>
    </div>
  );
};

export default SessionsRow;
