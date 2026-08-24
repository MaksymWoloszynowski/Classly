import { useCalendarController } from "@fullcalendar/react";
import api from "../../api/api";
import Calendar from "../../components/calendar/Calendar";
import { useEffect, useState } from "react";
import useAuth from "../../hooks/useAuth";
import LoadingOverlay from "../../components/loadingOverlay/LoadingOverlay";
import type { Assessment } from "../../types/assessment";

const Assessments = () => {
  const [assessments, setAssessments] = useState<Assessment[] | null>(null);
  const [loading, setLoading] = useState<boolean>(true)

  const controller = useCalendarController();
  const { auth } = useAuth();

  const fetchAssessments = async () => {
    try {
      const response = await api.get(
        `/api/assessment/date?groupId=${auth?.student?.groupId}&from=${controller.view?.activeStart.toLocaleDateString("en-CA")}&to=${controller.view?.activeEnd.toLocaleDateString("en-CA")}`,
      );

      setAssessments(response.data);
    } catch (error) {
      console.error("Error fetching assessments:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAssessments();
  }, []);

  const events =
    assessments?.map((item: Assessment) => ({
      title: item.type,
      start: item.dateDue,
      end: item.dateDue,
      extendedProps: {
        subject: item.subjectName,
      },
    })) ?? [];

  const renderEventContent = (eventInfo: any) => {
    return (
        <div>{eventInfo.event.title} - {eventInfo.event.extendedProps.subject}</div>
    );
  };

  return (
    <>
    {loading && <LoadingOverlay />}
    <Calendar controller={controller} action={fetchAssessments} events={events} renderEventContent={renderEventContent} />
    </>
  );
};

export default Assessments;
