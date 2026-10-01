import { useEffect, useState } from "react";
import { Search, X } from "lucide-react";
import api from "../../../api/api";
import ErrorMessage from "../../../components/errorMessage/ErrorMessage";
import LoadingOverlay from "../../../components/loadingOverlay/LoadingOverlay";
import { useTranslation } from "../../../hooks/useTranslation";
import type { AdminTeacher } from "../../../types/domain/teacher";
import styles from "../AdminPages.module.css";
import AdminAddButton from "@components/adminAddButton/AdminAddButton";
import AdminTable, {
  type AdminTableColumn,
} from "@components/adminTable/AdminTable";
import PaginationFooter from "@components/paginationFooter/PaginationFooter";
import FormFooterButtons from "@components/formFooterButtons/FormFooterButtons";
import SensitiveData from "@components/sensitiveData/SensitiveData";

type TeacherForm = {
  firstName: string;
  lastName: string;
  socialId: string;
};

const emptyForm: TeacherForm = {
  firstName: "",
  lastName: "",
  socialId: "",
};

const TeachersPerPage = 10;

type SortOption = "lastName,asc" | "firstName,asc" | "dateOfBirth,desc";

type TeacherPage = {
  content: AdminTeacher[];
  totalElements: number;
  totalPages: number;
};

const AdminTeachers = () => {
  const { t } = useTranslation();
  const [teachers, setTeachers] = useState<AdminTeacher[]>([]);
  const [search, setSearch] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [sort, setSort] = useState<SortOption>("lastName,asc");
  const [totalTeachers, setTotalTeachers] = useState(0);
  const [pageCount, setPageCount] = useState(1);
  const [form, setForm] = useState<TeacherForm>(emptyForm);
  const [editingTeacher, setEditingTeacher] = useState<AdminTeacher | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(false);
  const loadData = async () => {
    try {
      setLoading(true);
      setError(false);
      const teachersResponse = await api.get<TeacherPage>("/api/admin/teacher", {
        params: {
          page: currentPage - 1,
          size: TeachersPerPage,
          sort,
          search: search.trim() || undefined,
        },
      });

      setTeachers(teachersResponse.data.content);
      setTotalTeachers(teachersResponse.data.totalElements);
      setPageCount(Math.max(1, teachersResponse.data.totalPages));
    } catch {
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, [currentPage, search, sort]);

  useEffect(() => {
    setCurrentPage(1);
  }, [search, sort]);

  const columns: AdminTableColumn<AdminTeacher>[] = [
    {
      key: "number",
      header: t("number"),
      render: (_teacher, index) => `${index + 1}.`,
    },
    {
      key: "name",
      header: t("teacher"),
      render: (teacher) => `${teacher.lastName} ${teacher.firstName}`,
    },
    {
      key: "socialId",
      header: t("socialId"),
      render: (teacher) => <SensitiveData value={teacher.socialId} />,
    },
    {
      key: "teachingAssignments",
      header: t("teachingAssignments"),
      render: (teacher) => teacher.teachingAssignments.length.toString(),
    },
  ];

  const openCreateModal = () => {
    setEditingTeacher(null);
    setForm(emptyForm);
    setIsModalOpen(true);
  };

  const openEditModal = (teacher: AdminTeacher) => {
    setEditingTeacher(teacher);
    setForm({
      firstName: teacher.firstName,
      lastName: teacher.lastName,
      socialId: teacher.socialId,
    });
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
        firstName: form.firstName.trim(),
        lastName: form.lastName.trim(),
      };
      if (editingTeacher) {
        await api.put<AdminTeacher>(`/api/teacher/${editingTeacher.id}`, payload);
      } else {
        await api.post<AdminTeacher>("/api/teacher", payload);
      }
      await loadData();
      setIsModalOpen(false);
    } catch {
      setError(true);
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (teacher: AdminTeacher) => {
    if (
      !window.confirm(
        `${t("deleteTeacherConfirm")} ${teacher.firstName} ${teacher.lastName}?`,
      )
    )
      return;
    try {
      await api.delete(`/api/teacher/${teacher.id}`);
      if (teachers.length === 1 && currentPage > 1) {
        setCurrentPage((page) => page - 1);
      } else {
        await loadData();
      }
    } catch {
      setError(true);
    }
  };

  return (
    <section className={styles.page}>
      {loading && <LoadingOverlay />}
      {error && <ErrorMessage message={t("adminTeachersError")} />}

      <header className={styles.header}>
        <div className={styles.pageTitle}>{t("adminTeachers")}</div>
        <AdminAddButton onClick={openCreateModal} text={t("addTeacher")} />
      </header>

      <div className={styles.toolbar}>
        <div className={styles.searchBox}>
          <Search size={18} />
          <input
            aria-label={t("searchTeachers")}
            value={search}
            onChange={(event) => setSearch(event.target.value)}
            placeholder={t("searchTeachers")}
          />
        </div>
        <span className={styles.count}>
          {totalTeachers} {t("teachersCount")}
        </span>
        <label className={styles.sortControl}>
          <span>{t("sortBy")}</span>
          <select
            value={sort}
            onChange={(event) => {
              setCurrentPage(1);
              setSort(event.target.value as SortOption);
            }}
          >
            <option value="lastName,asc">{t("sortLastName")}</option>
            <option value="firstName,asc">{t("sortFirstName")}</option>
            <option value="dateOfBirth,desc">{t("sortDateOfBirth")}</option>
          </select>
        </label>
      </div>

      <AdminTable
        items={teachers}
        columns={columns}
        loading={loading}
        emptyMessage={t("noTeachers")}
        actionsLabel={t("actions")}
        editLabel={t("edit")}
        deleteLabel={t("delete")}
        onEdit={openEditModal}
        onDelete={(teacher) => handleDelete(teacher)}
      />

      {totalTeachers > 0 && (
        <PaginationFooter
          currentPage={currentPage}
          pageCount={pageCount}
          setCurrentPage={setCurrentPage}
        />
      )}

      {isModalOpen && (
        <div className={styles.overlay} onMouseDown={closeModal}>
          <div
            className={styles.modal}
            aria-modal="true"
            aria-labelledby="teacher-modal-title"
            onMouseDown={(event) => event.stopPropagation()}
          >
            <div className={styles.modalHeader}>
              <div>
                <div id="teacher-modal-title">
                  {editingTeacher ? t("editTeacher") : t("newTeacher")}
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
                {t("firstName")}
                <input
                  required
                  value={form.firstName}
                  onChange={(event) =>
                    setForm({ ...form, firstName: event.target.value })
                  }
                />
              </label>
              <label>
                {t("lastName")}
                <input
                  required
                  value={form.lastName}
                  onChange={(event) =>
                    setForm({ ...form, lastName: event.target.value })
                  }
                />
              </label>
              <label>
                {t("socialId")}
                <input
                  required
                  value={form.socialId}
                  onChange={(event) =>
                    setForm({ ...form, socialId: event.target.value })
                  }
                />
              </label>
              <FormFooterButtons
                saving={saving}
                onClose={closeModal}
                saveText={editingTeacher ? t("saveChanges") : t("addTeacher")}
              />
            </form>
          </div>
        </div>
      )}
    </section>
  );
};

export default AdminTeachers;
