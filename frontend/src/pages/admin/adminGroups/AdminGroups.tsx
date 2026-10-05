import { useEffect, useState } from "react";
import { X } from "lucide-react";
import styles from "../AdminPages.module.css";
import AdminAddButton from "@components/adminAddButton/AdminAddButton";
import AdminTable, {
  type AdminTableColumn,
} from "@components/adminTable/AdminTable";
import FormFooterButtons from "@components/formFooterButtons/FormFooterButtons";
import type { Group } from "@types-local/index";
import { useTranslation } from "@hooks/useTranslation";
import api from "@api/api";
import ErrorMessage from "@components/errorMessage/ErrorMessage";
import LoadingOverlay from "@components/loadingOverlay/LoadingOverlay";
import type { Student } from "@types-local/domain/student";

const AdminGroups = () => {
  const { t } = useTranslation();
  const [groups, setGroups] = useState<Group[]>([]);
  const [editingGroup, setEditingGroup] = useState<Group | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(false);
  const [formGroupName, setFormGroupName] = useState("");
  const [isStudentModalOpen, setIsStudentModalOpen] = useState(false);
  const [selectedGroup, setSelectedGroup] = useState<Group | null>(null);
  const [students, setStudents] = useState<Student[]>([]);
  const [selectedStudentIds, setSelectedStudentIds] = useState<string[]>([]);
  const [studentsSaving, setStudentsSaving] = useState(false);

  const loadData = async () => {
    try {
      setLoading(true);
      setError(false);
      const groupsResponse = await api.get<Group[]>("/api/group");
      setGroups(groupsResponse.data);
    } catch {
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const columns: AdminTableColumn<Group>[] = [
    {
      key: "number",
      header: t("number"),
      render: (_group, index) => `${index + 1}.`,
    },
    {
      key: "name",
      header: t("group"),
      render: (group) => group.name,
    },
    {
      key: "studentsCount",
      header: t("studentsCount"),
      render: (group) => group.students.length.toString(),
    },
    {
      key: "homeroomTeacher",
      header: t("homeroomTeacher"),
      render: (group) => group.homeroomTeacher || t("noHomeroomTeacher"),
    }
  ];

  const openCreateModal = () => {
    setEditingGroup(null);
    setIsModalOpen(true);
  };


  const openEditModal = (group: Group) => {
    setEditingGroup(group);
    setFormGroupName(group.name);
    setIsModalOpen(true);
  };

  const openStudentsModal = async (group: Group) => {
    setSelectedGroup(group);
    setSelectedStudentIds(group.students.map((student) => student.id));
    setIsStudentModalOpen(true);
    await loadStudents();
  };

  const loadStudents = async () => {
    try {
      const response = await api.get<{ content: Student[] }>("/api/student", {
        params: { page: 0, size: 1000, sort: "lastName,asc" },
      });
      setStudents(response.data.content);
    } catch {
      setError(true);
    }
  };

  const toggleStudent = (studentId: string) => {
    setSelectedStudentIds((current) =>
      current.includes(studentId)
        ? current.filter((id) => id !== studentId)
        : [...current, studentId],
    );
  };

  const handleStudentsSubmit = async () => {
    if (!selectedGroup) return;
    const currentIds = new Set(
      selectedGroup.students.map((student) => student.id),
    );
    const selectedIds = new Set(selectedStudentIds);
    const addedIds = selectedStudentIds.filter((id) => !currentIds.has(id));
    const removedIds = [...currentIds].filter((id) => !selectedIds.has(id));

    try {
      setStudentsSaving(true);
      await Promise.all([
        ...addedIds.map((studentId) =>
          api.post(`/api/student/${studentId}/group/${selectedGroup.id}`),
        ),
        ...removedIds.map((studentId) =>
          api.delete(`/api/student/${studentId}/group`),
        ),
      ]);
      await loadData();
      setIsStudentModalOpen(false);
      setSelectedGroup(null);
    } catch {
      setError(true);
    } finally {
      setStudentsSaving(false);
    }
  };

  const closeModal = () => {
    if (!saving) setIsModalOpen(false);
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      setSaving(true);
      const payload = {
        name: formGroupName.trim(),
      };
      if (editingGroup) {
        await api.put<Group>(`/api/group/${editingGroup.id}`, payload);
      } else {
        await api.post<Group>("/api/group", payload);
      }
      await loadData();
      setIsModalOpen(false);
    } catch {
      setError(true);
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (group: Group) => {
    if (
      !window.confirm(
        `${t("deleteGroupConfirm")} ${group.name}?`,
      )
    )
      return;
    try {
      await api.delete(`/api/group/${group.id}`);
        await loadData();
    } catch {
      setError(true);
    }
  };

  return (
    <section className={styles.page}>
      {loading && <LoadingOverlay />}
      {error && <ErrorMessage message={t("adminGroupsError")} />}

      <header className={styles.header}>
        <div className={styles.pageTitle}>{t("adminGroups")}</div>
        <AdminAddButton onClick={openCreateModal} text={t("addGroup")} />
      </header>

      <AdminTable
        items={groups}
        columns={columns}
        loading={loading}
        emptyMessage={t("noGroups")}
        actionsLabel={t("actions")}
        editLabel={t("edit")}
        deleteLabel={t("delete")}
        onEdit={openEditModal}
        onDelete={(group) => handleDelete(group)}
        onRowClick={(group) => void openStudentsModal(group)}
      />

      {isStudentModalOpen && (
        <div className={styles.overlay} onMouseDown={() => setIsStudentModalOpen(false)}>
          <div
            className={styles.modal}
            aria-modal="true"
            aria-labelledby="group-students-modal-title"
            onMouseDown={(event) => event.stopPropagation()}
          >
            <div className={styles.modalHeader}>
              <div>
                <div id="group-students-modal-title">
                  {t("groupStudents")}
                </div>
              </div>
              <button
                className={styles.closeButton}
                type="button"
                onClick={() => setIsStudentModalOpen(false)}
                aria-label={t("close")}
              >
                <X size={20} />
              </button>
            </div>
            <form
              className={styles.form}
              onSubmit={(event) => {
                event.preventDefault();
                void handleStudentsSubmit();
              }}
            >
              <section className={styles.studentsList}>
                {students.length === 0 ? (
                  <p>{t("noStudents")}</p>
                ) : (
                  students.map((student) => (
                    <label key={student.id} className={styles.studentItem}>
                      <input
                        type="checkbox"
                        checked={selectedStudentIds.includes(student.id)}
                        onChange={() => toggleStudent(student.id)}
                      />
                      {student.lastName} {student.firstName}
                    </label>
                  ))
                )}
              </section>
              <FormFooterButtons
                saving={studentsSaving}
                onClose={() => setIsStudentModalOpen(false)}
                saveText={t("saveChanges")}
              />
            </form>
          </div>
        </div>
      )}

      {isModalOpen && (
        <div className={styles.overlay} onMouseDown={closeModal}>
          <div
            className={styles.modal}
            aria-modal="true"
            aria-labelledby="group-modal-title"
            onMouseDown={(event) => event.stopPropagation()}
          >
            <div className={styles.modalHeader}>
              <div>
                <div id="group-modal-title">
                  {editingGroup ? t("editGroup") : t("newGroup")}
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
              <label>
                {t("groupName")}
                <input
                  required
                  value={formGroupName}
                  onChange={(event) =>
                    setFormGroupName(event.target.value)
                  }
                />
              </label>
              <FormFooterButtons
                saving={saving}
                onClose={closeModal}
                saveText={editingGroup ? t("saveChanges") : t("addGroup")}
              />
            </form>
          </div>
        </div>
      )}
    </section>
  );
};

export default AdminGroups;
