import type { EventDisplayInfo } from "@fullcalendar/react";
import styles from "./ScheduleEvent.module.css";
import useAuth from "@hooks/useAuth";
import { CheckIcon } from "lucide-react";

interface EventDisplayProps {
  eventInfo: EventDisplayInfo;
}

const ScheduleEvent = ({ eventInfo }: EventDisplayProps) => {
  const { auth } = useAuth();
  const { teacher, room, override, group, realized } = eventInfo.event.extendedProps;

  const isTeacher = auth?.role === "ROLE_TEACHER";

  return (
    <div className={` ${isTeacher && styles.teacherEvent} ${styles.event}`}>
      <div className={styles.eventHeader}>
        <div className={styles.time}>
          {eventInfo.event.start?.toLocaleTimeString("pl-PL", {
            hour: "2-digit",

            minute: "2-digit",
          })}{" "}
          -{" "}
          {eventInfo.event.end?.toLocaleTimeString("pl-PL", {
            hour: "2-digit",

            minute: "2-digit",
          })}
        </div>
        {override && (
          <div className={`${styles.override} ${styles[override.type]}`}>
            {override.type}
          </div>
        )}
        {isTeacher && realized && <div className={styles.realized}><CheckIcon  className={styles.checkIcon}/></div>}
      </div>

      <div>
        <div className={styles.subject}>{eventInfo.event.title}</div>

        <div className={styles.details}>
          <span>{isTeacher ? `Group: ${group}` : teacher}</span>

          <span>Room: {room}</span>
        </div>
      </div>
    </div>
  );
};

export default ScheduleEvent;
