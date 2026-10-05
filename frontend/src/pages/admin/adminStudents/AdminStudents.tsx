import { useEffect, useState } from "react";
import { Search, X } from "lucide-react";
import api from "../../../api/api";
import ErrorMessage from "../../../components/errorMessage/ErrorMessage";
import LoadingOverlay from "../../../components/loadingOverlay/LoadingOverlay";
import { useTranslation } from "../../../hooks/useTranslation";
import type { GroupSummary } from "../../../types/domain/groupSummary";
import type { AdminStudent } from "../../../types/domain/student";
import styles from "../AdminPages.module.css";
import AdminAddButton from "@components/adminAddButton/AdminAddButton";
import AdminTable, {
  type AdminTableColumn,
} from "@components/adminTable/AdminTable";
import PaginationFooter from "@components/paginationFooter/PaginationFooter";
import FormFooterButtons from "@components/formFooterButtons/FormFooterButtons";
import SensitiveData from "@components/sensitiveData/SensitiveData";

type StudentForm = {
  firstName: string;
  lastName: string;
  socialId: string;
  dateOfBirth: string;
  groupId: string;
};

const emptyForm: StudentForm = {
  firstName: "",
  lastName: "",
    socialId: "",
  dateOfBirth: "",
  groupId: "",
};

const studentsPerPage = 10;

type SortOption = "lastName,asc" | "firstName,asc" | "dateOfBirth,desc";

type StudentPage = {
  content: AdminStudent[];
  totalElements: number;
  totalPages: number;
};

const AdminStudents = () => {
  const { t } = useTranslation();
  const [students, setStudents] = useState<AdminStudent[]>([]);
  const [groups, setGroups] = useState<GroupSummary[]>([]);
  const [search, setSearch] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [sort, setSort] = useState<SortOption>("lastName,asc");
  const [totalStudents, setTotalStudents] = useState(0);
  const [pageCount, setPageCount] = useState(1);
  const [form, setForm] = useState<StudentForm>(emptyForm);
  const [editingStudent, setEditingStudent] = useState<AdminStudent | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(false);
  const loadData = async () => {
    try {
      setLoading(true);
      setError(false);
      const [studentsResponse, groupsResponse] = await Promise.all([
        api.get<StudentPage>("/api/admin/student", {
          params: {
            page: currentPage - 1,
            size: studentsPerPage,
            sort,
            search: search.trim() || undefined,
          },
        }),
        api.get<GroupSummary[]>("/api/group/summary"),
      ]);
      setStudents(studentsResponse.data.content);
      setTotalStudents(studentsResponse.data.totalElements);
      setPageCount(Math.max(1, studentsResponse.data.totalPages));
      setGroups(groupsResponse.data);
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

  const columns: AdminTableColumn<AdminStudent>[] = [
    {
      key: "number",
      header: t("number"),
      render: (_student, index) => `${index + 1+studentsPerPage*(currentPage-1)}.`,
    },
    {
      key: "name",
      header: t("student"),
      render: (student) => `${student.lastName} ${student.firstName}`,
    },
    {
      key: "dateOfBirth",
      header: t("dateOfBirth"),
      render: (student) => String(student.dateOfBirth).slice(0, 10),
    },
    {
      key: "socialId",
      header: t("socialId"),
      render: (student) => <SensitiveData value={student.socialId} />,
    },
    {
      key: "group",
      header: t("group"),
      render: (student) => student.groupName || t("noGroup"),
    },
  ];

  const openCreateModal = () => {
    setEditingStudent(null);
    setForm(emptyForm);
    setIsModalOpen(true);
  };

  const openEditModal = (student: AdminStudent) => {
    setEditingStudent(student);
    setForm({
      firstName: student.firstName,
      lastName: student.lastName,
      dateOfBirth: String(student.dateOfBirth).slice(0, 10),
      socialId: student.socialId,
      groupId: student.groupId ?? "",
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
        socialId: form.socialId.trim(),
        dateOfBirth: form.dateOfBirth,
        groupId: form.groupId || null,
      };
      if (editingStudent) {
        await api.put<AdminStudent>(`/api/student/${editingStudent.id}`, payload);
      } else {
        await api.post<AdminStudent>("/api/student", payload);
      }
      await loadData();
      setIsModalOpen(false);
    } catch {
      setError(true);
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (student: AdminStudent) => {
    if (
      !window.confirm(
        `${t("deleteStudentConfirm")} ${student.firstName} ${student.lastName}?`,
      )
    )
      return;
    try {
      await api.delete(`/api/student/${student.id}`);
      if (students.length === 1 && currentPage > 1) {
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
      {error && <ErrorMessage message={t("adminStudentsError")} />}

      <header className={styles.header}>
        <div className={styles.pageTitle}>{t("adminStudents")}</div>
        <AdminAddButton onClick={openCreateModal} text={t("addStudent")} />
      </header>

      <div className={styles.toolbar}>
        <div className={styles.searchBox}>
          <Search size={18} />
          <input
            aria-label={t("searchStudents")}
            value={search}
            onChange={(event) => setSearch(event.target.value)}
            placeholder={t("searchStudents")}
          />
        </div>
        <span className={styles.count}>
          {totalStudents} {t("studentsCount")}
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
        items={students}
        columns={columns}
        loading={loading}
        emptyMessage={t("noStudents")}
        actionsLabel={t("actions")}
        editLabel={t("edit")}
        deleteLabel={t("delete")}
        onEdit={openEditModal}
        onDelete={(student) => handleDelete(student)}
      />

      {totalStudents > 0 && (
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
            aria-labelledby="student-modal-title"
            onMouseDown={(event) => event.stopPropagation()}
          >
            <div className={styles.modalHeader}>
              <div>
                <div id="student-modal-title">
                  {editingStudent ? t("editStudent") : t("newStudent")}
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
              <label>
                {t("dateOfBirth")}
                <input
                  required
                  type="date"
                  value={form.dateOfBirth}
                  onChange={(event) =>
                    setForm({ ...form, dateOfBirth: event.target.value })
                  }
                />
              </label>
              <label>
                {t("group")}
                <select
                  value={form.groupId}
                  onChange={(event) =>
                    setForm({ ...form, groupId: event.target.value })
                  }
                >
                  <option value="">{t("noGroup")}</option>
                  {groups.map((group) => (
                    <option key={group.id} value={group.id}>
                      {group.name}
                    </option>
                  ))}
                </select>
              </label>
              <FormFooterButtons
                saving={saving}
                onClose={closeModal}
                saveText={editingStudent ? t("saveChanges") : t("addStudent")}
              />
            </form>
          </div>
        </div>
      )}
    </section>
  );
};

export default AdminStudents;
