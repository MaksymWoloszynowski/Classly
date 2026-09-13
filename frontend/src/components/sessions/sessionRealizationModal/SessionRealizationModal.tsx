import { useEffect, useState } from "react";
import { X } from "lucide-react";
import api from "@api/api";
import type { Schedule } from "../../../types/domain/schedule";
import type { Session } from "../../../types/domain/session";
import type { StudentSummary } from "../../../types/domain/studentSummary";
import type { Attendance } from "../../../types/domain/attendance";
import { attendanceTypes, type AttendanceType } from "../../../types/enums";
import type { Group } from "../../../types/domain/group";
import type { TeachingAssignment } from "../../../types/domain/teachingAssignment";
import styles from "./SessionRealizationModal.module.css";
import { attendanceTypeLabels } from "@types-local/labels";

type SessionRealizationModalProps = {
  occurrence: Schedule;
  session?: Session;
  onClose: () => void;
  onSaved: (session: Session) => void;
};

const SessionRealizationModal = ({
  occurrence,
  session,
  onClose,
  onSaved,
}: SessionRealizationModalProps) => {
  const [description, setDescription] = useState(session?.description ?? "");
  const [students, setStudents] = useState<StudentSummary[]>([]);
  const [attendance, setAttendance] = useState<Record<string, AttendanceType>>(
    {},
  );
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const assignment = await api.get<TeachingAssignment>(
          `/api/teaching-assignment/${occurrence.teachingAssignmentId}`,
        );
        const group = await api.get<Group>(
          `/api/group/${assignment.data.groupId}`,
        );
        const existing = session
          ? await api.get<Attendance[]>(
              `/api/attendance?sessionId=${session.id}`,
            )
          : null;
        const values = Object.fromEntries(
          group.data.students.map((student) => [
            student.id,
            existing?.data.find((entry) => entry.studentId === student.id)
              ?.type ?? "PRESENT",
          ]),
        ) as Record<string, AttendanceType>;
        setStudents(group.data.students);
        setAttendance(values);
      } catch {
        setError("Error fetching students.");
      } finally {
        setLoading(false);
      }
    };
    load();
  }, [occurrence.teachingAssignmentId, session]);

  const submit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (!occurrence.scheduleId) {
      setError(
        "Nie można zrealizować zajęć, które nie pochodzą z regularnego planu.",
      );
      return;
    }

    setSubmitting(true);
    setError(null);

    try {
      const request = {
        scheduleId: occurrence.scheduleId,
        teachingAssignmentId: occurrence.teachingAssignmentId,
        date: occurrence.date,
        startTime: occurrence.startTime,
        endTime: occurrence.endTime,
        description: description.trim(),
      };
      const response = session
        ? await api.put<Session>(`/api/session/${session.id}`, request)
        : await api.post<Session>("/api/session", request);
      if (session) {
        const existing = await api.get<Attendance[]>(
          `/api/attendance?sessionId=${session.id}`,
        );
        await Promise.all(
          existing.data.map((entry) =>
            api.delete(`/api/attendance/${entry.id}`),
          ),
        );
      }
      await api.post("/api/attendance", {
        sessionId: response.data.id,
        entries: students.map((student) => ({
          studentId: student.id,
          type: attendance[student.id] ?? "PRESENT",
        })),
      });
      onSaved(response.data);
      onClose();
    } catch (requestError: unknown) {
      setError("Failed to save realization.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className={styles.overlay} onClick={onClose}>
      <form
        className={styles.modal}
        onSubmit={submit}
        onClick={(event) => event.stopPropagation()}
      >
        <div className={styles.header}>
          <div>
            <div>{occurrence.subjectName} · Group {occurrence.groupName}</div>
            <p>
              {occurrence.date}, {occurrence.startTime.slice(0, 5)}–
              {occurrence.endTime.slice(0, 5)}
            </p>
          </div>
          <button
            type="button"
            className={styles.close}
            onClick={onClose}
            aria-label="Close"
          >
            <X />
          </button>
        </div>

        <label className={styles.label} htmlFor="session-description">
          Description (optional)
        </label>
        <textarea
          id="session-description"
          className={styles.textarea}
          value={description}
          onChange={(event) => setDescription(event.target.value)}
          maxLength={255}
          required
          autoFocus
        />

        <div className={styles.attendanceTitle}>Attendance</div>
        {loading ? (
          <p>Loading students...</p>
        ) : (
          <div className={styles.attendance}>
            {students.map((student) => (
              <label className={styles.student} key={student.id}>
                <span>
                  {student.lastName} {student.firstName}
                </span>
                <select
                  value={attendance[student.id] ?? "PRESENT"}
                  onChange={(event) =>
                    setAttendance((current) => ({
                      ...current,
                      [student.id]: event.target.value as AttendanceType,
                    }))
                  }
                >
                  {attendanceTypes.map((assessmentType) => (
                    <option key={assessmentType} value={assessmentType}>
                      {attendanceTypeLabels[assessmentType]}
                    </option>
                  ))}
                </select>
              </label>
            ))}
          </div>
        )}

        {error && <p className={styles.error}>{error}</p>}

        <div className={styles.actions}>
          <button type="button" className={styles.cancel} onClick={onClose}>
            Cancel
          </button>
          <button
            type="submit"
            className={styles.submit}
            disabled={submitting || loading || students.length === 0}
          >
            {submitting
              ? "Saving..."
              : session
                ? "Save Changes"
                : "Mark as Realized"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default SessionRealizationModal;
