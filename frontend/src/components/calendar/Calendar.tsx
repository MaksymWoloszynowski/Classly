import styles from "./Calendar.module.css";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/react/daygrid";
import timeGridPlugin from "@fullcalendar/react/timegrid";
import "@fullcalendar/react/skeleton.css";
import "@fullcalendar/react/themes/monarch/theme.css";
import "@fullcalendar/react/themes/monarch/palettes/purple.css";
import type { CalendarController } from "@fullcalendar/react";

interface CalendarProps {
  controller: CalendarController;
  action: () => void;
  events: any[];
  renderEventContent: (eventInfo: any) => React.ReactNode;
}

const Calendar = ({
  controller,
  action,
  events,
  renderEventContent,
}: CalendarProps) => {
  return (
    <section className={styles.schedule}>
      <div className="toolbar">
        <button
          onClick={() => {
            controller.prev();
            action();
          }}
        >
          Prev
        </button>
        <button
          onClick={() => {
            controller.next();
            action();
          }}
        >
          Next
        </button>
        <button onClick={() => controller.changeView("timeGridWeek")}>
          Week
        </button>
        <button onClick={() => controller.changeView("dayGridMonth")}>
          Month
        </button>
        <div className="toolbar-title">{controller.view?.title}</div>
      </div>

      <div className={styles.calendarContainer}>
        <FullCalendar
          controller={controller}
          plugins={[dayGridPlugin, timeGridPlugin]}
          initialView="timeGridWeek"
          showNonCurrentDates={false}
          slotMinTime="08:00:00"
          slotMaxTime="20:00:00"
          firstDay={1}
          events={events}
          eventContent={renderEventContent}
        />
      </div>
    </section>
  );
};

export default Calendar;
