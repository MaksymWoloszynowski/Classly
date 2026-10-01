import { useEffect, useState } from "react";
import {  X } from "lucide-react";
import styles from "../AdminPages.module.css";
import AdminAddButton from "@components/adminAddButton/AdminAddButton";
import AdminTable, {
  type AdminTableColumn,
} from "@components/adminTable/AdminTable";
import FormFooterButtons from "@components/formFooterButtons/FormFooterButtons";
import type { Subject } from "@types-local/index";
import { useTranslation } from "@hooks/useTranslation";
import api from "@api/api";
import ErrorMessage from "@components/errorMessage/ErrorMessage";
import LoadingOverlay from "@components/loadingOverlay/LoadingOverlay";

const AdminSubjects = () => {
  const { t } = useTranslation();
  const [subjects, setSubjects] = useState<Subject[]>([]);
  const [editingSubject, setEditingSubject] = useState<Subject | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(false);
  const [formSubjectName, setFormSubjectName] = useState("");

  const loadData = async () => {
    try {
      setLoading(true);
      setError(false);
      const subjectsResponse = await api.get<Subject[]>("/api/subject");
      setSubjects(subjectsResponse.data);
    } catch {
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const columns: AdminTableColumn<Subject>[] = [
    {
      key: "number",
      header: t("number"),
      render: (_subject, index) => `${index + 1}.`,
    },
    {
      key: "name",
      header: t("subject"),
      render: (subject) => subject.name,
    }
  ];

  const openCreateModal = () => {
    setEditingSubject(null);
    setIsModalOpen(true);
  };


  const openEditModal = (subject: Subject) => {
    setEditingSubject(subject);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    if (!saving) setIsModalOpen(false);
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      setSaving(true);
      const payload = {
        name: formSubjectName.trim(),
      };
      if (editingSubject) {
        await api.put<Subject>(`/api/subject/${editingSubject.id}`, payload);
      } else {
        await api.post<Subject>("/api/subject", payload);
      }
      await loadData();
      setIsModalOpen(false);
    } catch {
      setError(true);
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (subject: Subject) => {
    if (
      !window.confirm(
        `${t("deleteSubjectConfirm")} ${subject.name}?`,
      )
    )
      return;
    try {
      await api.delete(`/api/subject/${subject.id}`);
        await loadData();
    } catch {
      setError(true);
    }
  };

  return (
    <section className={styles.page}>
      {loading && <LoadingOverlay />}
      {error && <ErrorMessage message={t("adminSubjectsError")} />}

      <header className={styles.header}>
        <div className={styles.pageTitle}>{t("adminSubjects")}</div>
        <AdminAddButton onClick={openCreateModal} text={t("addSubject")} />
      </header>

      <AdminTable
        items={subjects}
        columns={columns}
        loading={loading}
        emptyMessage={t("noSubjects")}
        actionsLabel={t("actions")}
        editLabel={t("edit")}
        deleteLabel={t("delete")}
        onEdit={openEditModal}
        onDelete={(subject) => handleDelete(subject)}
      />

      {isModalOpen && (
        <div className={styles.overlay} onMouseDown={closeModal}>
          <div
            className={styles.modal}
            aria-modal="true"
            aria-labelledby="subject-modal-title"
            onMouseDown={(event) => event.stopPropagation()}
          >
            <div className={styles.modalHeader}>
              <div>
                <div id="subject-modal-title">
                  {editingSubject ? t("editSubject") : t("newSubject")}
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
                {t("subject")}
                <input
                  required
                  value={formSubjectName}
                  onChange={(event) =>
                    setFormSubjectName(event.target.value)
                  }
                />
              </label>
              <FormFooterButtons
                saving={saving}
                onClose={closeModal}
                saveText={editingSubject ? t("saveChanges") : t("addSubject")}
              />
            </form>
          </div>
        </div>
      )}
    </section>
  );
};

export default AdminSubjects;
