import { useEffect, useState } from "react";
import { X } from "lucide-react";
import styles from "../AdminPages.module.css";
import AdminAddButton from "@components/adminAddButton/AdminAddButton";
import AdminTable, {
  type AdminTableColumn,
} from "@components/adminTable/AdminTable";
import FormFooterButtons from "@components/formFooterButtons/FormFooterButtons";
import type { ClassificationPeriod } from "@types-local/index";
import { useTranslation } from "@hooks/useTranslation";
import api from "@api/api";
import ErrorMessage from "@components/errorMessage/ErrorMessage";
import LoadingOverlay from "@components/loadingOverlay/LoadingOverlay";
import { formatDate } from "@utils/date";

const AdminClassificationPeriods = () => {
  const { t } = useTranslation();
  const [classificationPeriods, setClassificationPeriods] = useState<
    ClassificationPeriod[]
  >([]);
  const [editingClassificationPeriod, setEditingClassificationPeriod] =
    useState<ClassificationPeriod | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(false);
  const [formSemester, setFormSemester] = useState(1);
  const [formDateFrom, setFormDateFrom] = useState("");
  const [formDateTo, setFormDateTo] = useState("");
  const loadData = async () => {
    try {
      setLoading(true);
      setError(false);
      const classificationPeriodsResponse = await api.get<
        ClassificationPeriod[]
      >("/api/classification-period");
      setClassificationPeriods(classificationPeriodsResponse.data);
    } catch {
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const columns: AdminTableColumn<ClassificationPeriod>[] = [
    {
      key: "number",
      header: t("number"),
      render: (_classificationPeriod, index) => `${index + 1}.`,
    },
    {
      key: "semester",
      header: t("semester"),
      render: (classificationPeriod) => classificationPeriod.semester,
    },
    {
      key: "dateFrom",
      header: t("dateFrom"),
      render: (classificationPeriod) =>
        formatDate(new Date(classificationPeriod.dateFrom)),
    },
    {
      key: "dateTo",
      header: t("dateTo"),
      render: (classificationPeriod) =>
        formatDate(new Date(classificationPeriod.dateTo)),
    },
  ];

  const openCreateModal = () => {
    setEditingClassificationPeriod(null);
    setFormSemester(1);
    setFormDateFrom("");
    setFormDateTo("");
    setIsModalOpen(true);
  };

  const openEditModal = (classificationPeriod: ClassificationPeriod) => {
    setEditingClassificationPeriod(classificationPeriod);
    setFormSemester(classificationPeriod.semester);
    setFormDateFrom(String(classificationPeriod.dateFrom).slice(0, 10));
    setFormDateTo(String(classificationPeriod.dateTo).slice(0, 10));
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
        semester: formSemester,
        dateFrom: formDateFrom,
        dateTo: formDateTo,
      };
      if (editingClassificationPeriod) {
        await api.put<ClassificationPeriod>(
          `/api/classification-period/${editingClassificationPeriod.id}`,
          payload,
        );
      } else {
        await api.post<ClassificationPeriod>(
          "/api/classification-period",
          payload,
        );
      }
      await loadData();
      setIsModalOpen(false);
    } catch {
      setError(true);
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (classificationPeriod: ClassificationPeriod) => {
    if (
      !window.confirm(
        `${t("deleteClassificationPeriodConfirm")}?`,
      )
    )
      return;
    try {
      await api.delete(`/api/classification-period/${classificationPeriod.id}`);
      await loadData();
    } catch {
      setError(true);
    }
  };

  return (
    <section className={styles.page}>
      {loading && <LoadingOverlay />}
      {error && <ErrorMessage message={t("adminClassificationPeriodsError")} />}

      <header className={styles.header}>
        <div className={styles.pageTitle}>
          {t("adminClassificationPeriods")}
        </div>
        <AdminAddButton
          onClick={openCreateModal}
          text={t("addClassificationPeriod")}
        />
      </header>

      <AdminTable
        items={classificationPeriods}
        columns={columns}
        loading={loading}
        emptyMessage={t("noClassificationPeriods")}
        actionsLabel={t("actions")}
        editLabel={t("edit")}
        deleteLabel={t("delete")}
        onEdit={openEditModal}
        onDelete={(classificationPeriod) => handleDelete(classificationPeriod)}
      />

      {isModalOpen && (
        <div className={styles.overlay} onMouseDown={closeModal}>
          <div
            className={styles.modal}
            aria-modal="true"
            aria-labelledby="classificationPeriod-modal-title"
            onMouseDown={(event) => event.stopPropagation()}
          >
            <div className={styles.modalHeader}>
              <div>
                <div id="classificationPeriod-modal-title">
                  {editingClassificationPeriod
                    ? t("editClassificationPeriod")
                    : t("newClassificationPeriod")}
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
                {t("semester")}
                <select
                  value={formSemester}
                  onChange={(event) =>
                    setFormSemester(Number(event.target.value))
                  }
                >
                  <option value={1}>{t("firstSemester")}</option>
                  <option value={2}>{t("secondSemester")}</option>
                </select>
              </label>
              <label>
                {t("dateFrom")}
                <input
                  required
                  type="date"
                  value={formDateFrom}
                  onChange={(event) => setFormDateFrom(event.target.value)}
                />
              </label>
              <label>
                {t("dateTo")}
                <input
                  required
                  type="date"
                  min={formDateFrom || undefined}
                  value={formDateTo}
                  onChange={(event) => setFormDateTo(event.target.value)}
                />
              </label>
              <FormFooterButtons
                saving={saving}
                onClose={closeModal}
                saveText={
                  editingClassificationPeriod
                    ? t("saveChanges")
                    : t("addClassificationPeriod")
                }
              />
            </form>
          </div>
        </div>
      )}
    </section>
  );
};

export default AdminClassificationPeriods;
