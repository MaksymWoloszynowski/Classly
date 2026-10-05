import type { Attendance } from "@types-local/domain/attendance";
import styles from "./DashboardAttendanceItem.module.css";
import { useAttendanceTypeLabels, useSubjectLabels } from "@types-local/labels";
import { formatSubject } from "@utils/subject";

const DashboardAttendanceItem = ({
  attendance,
}: {
  attendance: Attendance;
}) => {
  const attendanceTypeLabels = useAttendanceTypeLabels();
  const subjectLabels = useSubjectLabels();

  return (
    <div key={attendance.id} className={styles.attendanceItem}>
      <div className={styles.attendanceTime}>
        {new Date(
          attendance.date + " " + attendance.startTime,
        ).toLocaleTimeString("pl-PL", { hour: "2-digit", minute: "2-digit" })}
        –
        {new Date(
          attendance.date + " " + attendance.endTime,
        ).toLocaleTimeString("pl-PL", {
          hour: "2-digit",
          minute: "2-digit",
        })}
      </div>

      <div className={styles.attendanceSubject}>{subjectLabels[formatSubject(attendance.subject)]}</div>

      <div className={`${styles.attendanceStatus} ${styles[attendance.type]}`}>
        {attendanceTypeLabels[attendance.type]}
      </div>
    </div>
  );
};

export default DashboardAttendanceItem;
