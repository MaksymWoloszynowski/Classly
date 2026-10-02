import { useEffect, useState } from "react";
import { Search, X } from "lucide-react";
import api from "../../../api/api";
import ErrorMessage from "../../../components/errorMessage/ErrorMessage";
import LoadingOverlay from "../../../components/loadingOverlay/LoadingOverlay";
import { useTranslation } from "../../../hooks/useTranslation";
import type { AdminParent, Parent } from "../../../types/domain/parent";
import type { StudentSummary } from "../../../types/domain/studentSummary";
import styles from "../AdminPages.module.css";
import AdminAddButton from "@components/adminAddButton/AdminAddButton";
import AdminTable, {
  type AdminTableColumn,
} from "@components/adminTable/AdminTable";
import PaginationFooter from "@components/paginationFooter/PaginationFooter";
import FormFooterButtons from "@components/formFooterButtons/FormFooterButtons";
import SensitiveData from "@components/sensitiveData/SensitiveData";

type ParentForm = { firstName: string; lastName: string; socialId: string };
type StudentPage = { content: StudentSummary[] };

type SortOption = "lastName,asc" | "firstName,asc";

type ParentPage = {
  content: AdminParent[];
  totalElements: number;
  totalPages: number;
};

const parentsPerPage = 10;
const emptyForm: ParentForm = { firstName: "", lastName: "", socialId: "" };

const AdminParents = () => {
  const { t } = useTranslation();
  const [parents, setParents] = useState<AdminParent[]>([]);
  const [search, setSearch] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [sort, setSort] = useState<SortOption>("lastName,asc");
  const [totalParents, setTotalParents] = useState(0);
  const [pageCount, setPageCount] = useState(1);
  const [form, setForm] = useState<ParentForm>(emptyForm);
  const [editingParent, setEditingParent] = useState<AdminParent | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isLinking, setIsLinking] = useState(false);
  const [students, setStudents] = useState<StudentSummary[]>([]);
  const [selectedStudentIds, setSelectedStudentIds] = useState<string[]>([]);
  const [linkingParent, setLinkingParent] = useState<AdminParent | null>(null);
  const [linkingSaving, setLinkingSaving] = useState(false);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(false);

  const loadData = async () => {
    try {
      setLoading(true);
      setError(false);
      const response = await api.get<ParentPage>("/api/admin/parent", {
        params: {
          page: currentPage - 1,
          size: parentsPerPage,
          sort,
          search: search.trim() || undefined,
        },
      });
      setParents(response.data.content);
      setTotalParents(response.data.totalElements);
      setPageCount(Math.max(1, response.data.totalPages));
    } catch {
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  const loadStudents = async () => {
    try {
      const response = await api.get<StudentPage>("/api/student/summary", {
        params: { page: 0, size: 1000, sort: "lastName,asc" },
      });
      setStudents(response.data.content);
    } catch {
      setError(true);
    }
  };

  useEffect(() => {
    loadData();
  }, [currentPage, search, sort]);

  useEffect(() => {
    setCurrentPage(1);
  }, [search, sort]);

  const columns: AdminTableColumn<AdminParent>[] = [
    {
      key: "number",
      header: t("number"),
      render: (_parent, index) => `${index + 1 + parentsPerPage*(currentPage-1)}.`,
    },
    {
      key: "name",
      header: t("parent"),
      render: (parent) => `${parent.lastName} ${parent.firstName}`,
    },
    {
      key: "socialId",
      header: t("socialId"),
      render: (parent) => <SensitiveData value={parent.socialId} />,
    },
    {
      key: "students",
      header: t("linkedStudents"),
      render: (parent) => parent.students?.length ?? 0,
    },
  ];

  const openCreateModal = () => {
    setEditingParent(null);
    setForm(emptyForm);
    setIsModalOpen(true);
  };

  const openEditModal = (parent: AdminParent) => {
    setEditingParent(parent);
    setForm({
      firstName: parent.firstName,
      lastName: parent.lastName,
      socialId: parent.socialId,
    });
    setIsModalOpen(true);
  };

  const openLinkModal = async (parent: AdminParent) => {
    setLinkingParent(parent);
    setSelectedStudentIds(parent.students?.map((student) => student.id) ?? []);
    setIsLinking(true);
    await loadStudents();
  };

  const toggleStudent = (studentId: string) => {
    setSelectedStudentIds((current) =>
      current.includes(studentId)
        ? current.filter((id) => id !== studentId)
        : [...current, studentId],
    );
  };

  const handleLinkSubmit = async () => {
    if (!linkingParent) return;
    const currentIds = new Set(
      linkingParent.students?.map((student) => student.id) ?? [],
    );
    const selectedIds = new Set(selectedStudentIds);
    const addedIds = selectedStudentIds.filter((id) => !currentIds.has(id));
    const removedIds = [...currentIds].filter((id) => !selectedIds.has(id));

    try {
      setLinkingSaving(true);
      await Promise.all([
        ...addedIds.map((studentId) =>
          api.post(`/api/parent/${linkingParent.id}/student/${studentId}`),
        ),
        ...removedIds.map((studentId) =>
          api.delete(`/api/parent/${linkingParent.id}/student/${studentId}`),
        ),
      ]);
      await loadData();
      setIsLinking(false);
      setLinkingParent(null);
    } catch {
      setError(true);
    } finally {
      setLinkingSaving(false);
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
        firstName: form.firstName.trim(),
        lastName: form.lastName.trim(),
        socialId: form.socialId.trim(),
      };
      if (editingParent) {
        await api.put<Parent>(`/api/parent/${editingParent.id}`, payload);
      } else {
        await api.post<Parent>("/api/parent", payload);
      }
      await loadData();
      setIsModalOpen(false);
    } catch {
      setError(true);
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (parent: AdminParent) => {
    if (
      !window.confirm(
        `${t("deleteParentConfirm")} ${parent.firstName} ${parent.lastName}?`,
      )
    )
      return;
    try {
      await api.delete(`/api/parent/${parent.id}`);
      if (parents.length === 1 && currentPage > 1) {
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
      {error && <ErrorMessage message={t("adminParentsError")} />}
      <header className={styles.header}>
        <div className={styles.pageTitle}>{t("adminParents")}</div>
        <AdminAddButton onClick={openCreateModal} text={t("addParent")} />
      </header>
      <div className={styles.toolbar}>
        <div className={styles.searchBox}>
          <Search size={18} />
          <input
            aria-label={t("searchParents")}
            value={search}
            onChange={(event) => setSearch(event.target.value)}
            placeholder={t("searchParents")}
          />
        </div>
        <span className={styles.count}>
          {totalParents} {t("parentsCount")}
        </span>
        <label className={styles.sortControl}>
          <span>{t("sortBy")}</span>
          <select
            value={sort}
            onChange={(event) => setSort(event.target.value as SortOption)}
          >
            <option value="lastName,asc">{t("sortLastName")}</option>
            <option value="firstName,asc">{t("sortFirstName")}</option>
          </select>
        </label>
      </div>
      <AdminTable
        items={parents}
        columns={columns}
        loading={loading}
        emptyMessage={t("noParents")}
        actionsLabel={t("actions")}
        editLabel={t("edit")}
        deleteLabel={t("delete")}
        linkLabel={t("linkParentToStudent")}
        onEdit={openEditModal}
        onDelete={(parent) => handleDelete(parent)}
        onLink={(parent) => void openLinkModal(parent)}
      />

      {totalParents > 0 && (
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
            aria-labelledby="parent-modal-title"
            onMouseDown={(event) => event.stopPropagation()}
          >
            <div className={styles.modalHeader}>
              <div className={styles.modalTitle}>
                {editingParent ? t("editParent") : t("newParent")}
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
                saveText={editingParent ? t("saveChanges") : t("addParent")}
              />
            </form>
          </div>
        </div>
      )}

      {isLinking && (
        <div className={styles.overlay} onMouseDown={() => setIsLinking(false)}>
          <div
            className={styles.modal}
            aria-modal="true"
            aria-labelledby="link-parent-modal-title"
            onMouseDown={(event) => event.stopPropagation()}
          >
            <div className={styles.modalHeader}>
              <div className={styles.modalTitle} id="link-parent-modal-title">
                {t("linkParentToStudent")}
              </div>
              <button
                className={styles.closeButton}
                type="button"
                onClick={() => setIsLinking(false)}
                aria-label={t("close")}
              >
                <X size={20} />
              </button>
            </div>
            <form
              className={styles.form}
              onSubmit={(event) => {
                event.preventDefault();
                void handleLinkSubmit();
              }}
            >
              <div className={styles.studentPicker}>
                {students.map((student) => (
                  <label className={styles.studentOption} key={student.id}>
                    <input
                      type="checkbox"
                      checked={selectedStudentIds.includes(student.id)}
                      onChange={() => toggleStudent(student.id)}
                    />
                    {student.lastName} {student.firstName}
                  </label>
                ))}
              </div>
              <FormFooterButtons
                saving={linkingSaving}
                onClose={() => setIsLinking(false)}
                saveText={t("saveLinks")}
              />
            </form>
          </div>
        </div>
      )}
    </section>
  );
};

export default AdminParents;
