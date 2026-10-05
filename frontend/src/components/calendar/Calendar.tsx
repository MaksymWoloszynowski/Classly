import styles from "./Calendar.module.css";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/react/daygrid";
import timeGridPlugin from "@fullcalendar/react/timegrid";
import interactionPlugin from "@fullcalendar/react/interaction"
import "@fullcalendar/react/skeleton.css";
import "@fullcalendar/react/themes/monarch/theme.css";
import "@fullcalendar/react/themes/monarch/palettes/purple.css";
import type {
  CalendarController,
  DateClickInfo,
  EventClickInfo,
  EventDisplayInfo,
  EventInput,
} from "@fullcalendar/react";

interface CalendarProps {
  controller: CalendarController;
  events: EventInput[];
  action: (start: Date, end: Date) => void;
  renderEventContent: (eventInfo: EventDisplayInfo) => React.ReactNode;
  initialView: string;
  headerToolbar: object;
  setViewType?: (type: string) => void;
  handleEventClick?: (info: EventClickInfo) => void;
  handleDateClick?: (info: DateClickInfo) => void;
}

const Calendar = ({
  controller,
  events,
  action,
  renderEventContent,
  initialView,
  headerToolbar,
  setViewType,
  handleEventClick,
  handleDateClick,
}: CalendarProps) => {
  return (
    <div className={styles.calendar}>
      <FullCalendar
        className={styles.cal}
        controller={controller}
        plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
        initialView={initialView}
        showNonCurrentDates={false}
        height="auto"
        fixedWeekCount={false}
        nowIndicator={true}
        slotMinTime="07:00:00"
        slotMaxTime="17:00:00"
        firstDay={1}
        events={events}
        slotMinHeight={60}
        tableHeaderSticky={false}
        allDaySlot={false}
        views={{
          timeGridWeek: {
            hiddenDays: [0, 6],
          },
          timeGridDay: {
            type: "timeGrid",
            duration: { days: 1 },
          },
        }}
        eventContent={renderEventContent}
        headerToolbar={headerToolbar}
        datesSet={(info) => {
          if (setViewType) setViewType(info.view.type);
          action(info.start, info.end);
        }}
        eventClick={handleEventClick}
        dateClick={handleDateClick}
        toolbarClass={styles.toolbar}
        toolbarTitleClass={styles.toolbarTitle}
        buttonClass={styles.button}
        buttonGroupClass={styles.buttons}
        dayCellClass={styles.dayCell}
        dayCellTopClass={styles.dayCellTop}
        dayCellTopInnerClass={styles.dayCellTopInner}
        dayCellInnerClass={styles.dayCellInner}
        dayCellBottomClass={styles.dayCellBottom}
        slotLaneClass={styles.slotLane}
        eventClass={styles.event}
      />
    </div>
  );
};

export default Calendar;
