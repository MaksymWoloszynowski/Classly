import { useCalendarController } from "@fullcalendar/react";
import api from "../../api/api";
import Calendar from "../../components/calendar/Calendar";
import { useEffect, useState } from "react";
import useAuth from "../../hooks/useAuth";
import type { Schedule } from "../../types/schedule";
import LoadingOverlay from "../../components/loadingOverlay/LoadingOverlay";

const Schedule = () => {
  const [schedule, setSchedule] = useState<Schedule[] | null>(null);
  const [loading, setLoading] = useState<boolean>(true)

  const controller = useCalendarController();
  const { auth } = useAuth();

  const fetchSchedule = async () => {
    try {
      const response = await api.get(
        `/api/schedule/date?groupId=${auth?.student?.groupId}&from=${controller.view?.activeStart.toLocaleDateString("en-CA")}&to=${controller.view?.activeEnd.toLocaleDateString("en-CA")}`,
      );

      setSchedule(response.data);
    } catch (error) {
      console.error("Error fetching schedule:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSchedule();
  }, []);

  const events =
    schedule?.map((item: Schedule) => ({
      title: item.subjectName,
      start: `${item.date}T${item.startTime}`,
      end: `${item.date}T${item.endTime}`,
      extendedProps: {
        teacher: item.teacherName,
        room: item.room,
      },
    })) ?? [];

  const renderEventContent = (eventInfo: any) => {
    return (
      <div>
        <div>
          {eventInfo.event.start?.toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit",
          })}{" "}
          -{" "}
          {eventInfo.event.end?.toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit",
          })}
        </div>

        <div>{eventInfo.event.title}</div>

        <div>{eventInfo.event.extendedProps.teacher}</div>

        <div>{eventInfo.event.extendedProps.room ?? "Brak sali"}</div>
      </div>
    );
  };

  return (
    <>
    {loading && <LoadingOverlay />}
    <Calendar controller={controller} action={fetchSchedule} events={events} renderEventContent={renderEventContent} />
    </>
  );
};

export default Schedule;
