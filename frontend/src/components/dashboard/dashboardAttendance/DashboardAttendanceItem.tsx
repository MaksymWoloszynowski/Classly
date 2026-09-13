import type { Attendance } from "@types-local/domain/attendance";
import styles from "./DashboardAttendanceItem.module.css";
import { attendanceTypeLabels } from "@types-local/labels";

const DashboardAttendanceItem = ({
  attendance,
}: {
  attendance: Attendance;
}) => {
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

      <div className={styles.attendanceSubject}>{attendance.subject}</div>

      <div
        className={`${styles.attendanceStatus} ${styles[attendance.type]}`}
      >
        {attendanceTypeLabels[attendance.type]}
      </div>
    </div>
  );
};

export default DashboardAttendanceItem;
