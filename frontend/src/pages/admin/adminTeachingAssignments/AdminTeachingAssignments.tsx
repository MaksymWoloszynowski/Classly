import type { TeachingAssignment } from "@types-local/domain/teachingAssignment";
import type { Group } from "@types-local/domain/group";
import { useEffect, useState } from "react";
import styles from "../AdminPages.module.css";
import api from "@api/api";
import type { Subject, Teacher } from "@types-local/index";
import LoadingOverlay from "@components/loadingOverlay/LoadingOverlay";
import ErrorMessage from "@components/errorMessage/ErrorMessage";
import AdminAddButton from "@components/adminAddButton/AdminAddButton";
import { useTranslation } from "@hooks/useTranslation";
import AdminTable, {
  type AdminTableColumn,
} from "@components/adminTable/AdminTable";
import FormFooterButtons from "@components/formFooterButtons/FormFooterButtons";
import { X } from "lucide-react";

const AdminTeachingAssignments = () => {
  const { t } = useTranslation();
  const [groups, setGroups] = useState<Group[]>([]);
  const [teachers, setTeachers] = useState<Teacher[]>([]);
  const [subjects, setSubjects] = useState<Subject[]>([]);
  const [teachingAssignmentToEdit, setTeachingAssignmentToEdit] =
    useState<TeachingAssignment | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(false);

  const [groupForm, setGroupForm] = useState<string>("");
  const [teacherForm, setTeacherForm] = useState<string>("");
  const [subjectForm, setSubjectForm] = useState<string>("");

  const loadData = async () => {
    try {
      setLoading(true);
      setError(false);
      const groupsResponse = await api.get("/api/group");
      setGroups(groupsResponse.data);
    } catch {
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  const loadTeachers = async () => {
    try {
      setLoading(true);
      const response = await api.get<{ content: Teacher[] }>("/api/teacher", {
        params: { page: 0, size: 1000, sort: "lastName,asc" },
      });
      setTeachers(response.data.content);
    } catch {
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  const loadSubjects = async () => {
    try {
      setLoading(true);
      const response = await api.get<Subject[]>("/api/subject");
      setSubjects(response.data);
    } catch {
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  const openCreateModal = async (group: Group) => {
    setTeachingAssignmentToEdit(null);
    setGroupForm(group.id);
    setIsModalOpen(true);
    await loadTeachers();
    await loadSubjects();
  };

  const openEditModal = async (teachingAssignment: TeachingAssignment) => {
    setTeachingAssignmentToEdit(teachingAssignment);
    setSubjectForm(teachingAssignment.subjectId);
    setGroupForm(teachingAssignment.groupId);
    setIsModalOpen(true);
    await loadTeachers();
  };

  useEffect(() => {
    loadData();
  }, []);

  const closeModal = () => {
    if (!saving) setIsModalOpen(false);
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      setSaving(true);
      const payload = {
        groupId: groupForm,
        subjectId: subjectForm,
        teacherId: teacherForm,
      };
      if (teachingAssignmentToEdit) {
        await api.put<Group>(
          `/api/teaching-assignment/${teachingAssignmentToEdit.id}`,
          payload,
        );
      } else {
        await api.post<Group>("/api/teaching-assignment", payload);
      }
      await loadData();
      setIsModalOpen(false);
    } catch {
      setError(true);
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (teachingAssignment: TeachingAssignment) => {
    if (
      !window.confirm(
        `${t("deleteGroupConfirm")} ${teachingAssignment.subjectName}?`,
      )
    )
      return;
    try {
      await api.delete(`/api/teaching-assignment/${teachingAssignment.id}`);
      await loadData();
    } catch {
      setError(true);
    }
  };

  const columns: AdminTableColumn<TeachingAssignment>[] = [
    {
      key: "number",
      header: t("number"),
      render: (_ta, index) => `${index + 1}.`,
    },
    {
      key: "subjectName",
      header: t("subject"),
      render: (ta) => ta.subjectName,
    },
    {
      key: "teacherName",
      header: t("teacher"),
      render: (ta) => ta.teacherName,
    },
  ];

  return (
    <section className={styles.page}>
      {loading && <LoadingOverlay />}
      {error && <ErrorMessage message={t("adminTeachingAssignmentsError")} />}

      <header className={styles.header}>
        <div className={styles.pageTitle}>{t("teachingAssignments")}</div>
      </header>

      {groups.map((group) => (
        <div key={group.id} className={styles.groupCard}>
          <div className={styles.groupHeader}>
              <div className={styles.groupName}>Group {group.name}</div>
              <AdminAddButton
                onClick={() => openCreateModal(group)}
                text={t("addTeachingAssignment")}
              />
          </div>
          <AdminTable
            items={group.teachingAssignments}
            columns={columns}
            loading={loading}
            emptyMessage={t("noTeachingAssignments")}
            actionsLabel={t("actions")}
            editLabel={t("edit")}
            deleteLabel={t("delete")}
            onEdit={openEditModal}
            onDelete={(teachingAssignment) => handleDelete(teachingAssignment)}
          />
        </div>
      ))}

      {isModalOpen && (
        <div className={styles.overlay} onMouseDown={closeModal}>
          <div
            className={styles.modal}
            aria-modal="true"
            aria-labelledby="assignment-modal-title"
            onMouseDown={(event) => event.stopPropagation()}
          >
            <div className={styles.modalHeader}>
              <div>
                <div id="assignment-modal-title">
                  {teachingAssignmentToEdit
                    ? t("editTeachingAssignment")
                    : t("newTeachingAssignment")}
                </div>
              </div>
              <button
                className={styles.closeButton}
                type="button"
                onClick={closeModal}
                aria-label={t("close")}
              >
                <X size={20} />
              </button>
            </div>
            <form className={styles.form} onSubmit={handleSubmit}>
              <select
                id="teacher"
                className={styles.input}
                value={teacherForm}
                onChange={(event) => setTeacherForm(event.target.value)}
                required
              >
                {teachers.map((teacher) => (
                  <option key={teacher.id} value={teacher.id}>
                    {teacher.lastName} {teacher.firstName}
                  </option>
                ))}
              </select>
              {!teachingAssignmentToEdit && (
                <select
                  id="subject"
                  className={styles.input}
                  value={subjectForm}
                  onChange={(event) => setSubjectForm(event.target.value)}
                  required
                >
                  <option value="">{t("chooseSubject")}</option>

                  {subjects.map((subject) => (
                    <option key={subject.id} value={subject.id}>
                      {subject.name}
                    </option>
                  ))}
                </select>
              )}
              <FormFooterButtons
                saving={saving}
                onClose={closeModal}
                saveText={
                  teachingAssignmentToEdit
                    ? t("saveChanges")
                    : t("newTeachingAssignment")
                }
              />
            </form>
          </div>
        </div>
      )}
    </section>
  );
};

export default AdminTeachingAssignments;
