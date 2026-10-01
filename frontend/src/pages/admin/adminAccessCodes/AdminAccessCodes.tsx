import { useEffect, useState } from "react";
import styles from "../AdminPages.module.css";
import AdminTable, {
  type AdminTableColumn,
} from "@components/adminTable/AdminTable";
import type { AccessCode } from "@types-local/index";
import { useTranslation } from "@hooks/useTranslation";
import api from "@api/api";
import ErrorMessage from "@components/errorMessage/ErrorMessage";
import LoadingOverlay from "@components/loadingOverlay/LoadingOverlay";
import PaginationFooter from "@components/paginationFooter/PaginationFooter";

type AccessCodePage = {
  content: AccessCode[];
  totalElements: number;
  totalPages: number;
};

const accessCodesPerPage = 10;

const AdminAccessCodes = () => {
  const { t } = useTranslation();
  const [accessCodes, setAccessCodes] = useState<AccessCode[]>([]);
  const [accessCodeRole, setAccessCodeRole] = useState<
    "student" | "teacher" | "parent"
  >("student");
  const [currentPage, setCurrentPage] = useState(1);
  const [totalAccessCodes, setTotalAccessCodes] = useState(0);
  const [pageCount, setPageCount] = useState(1);
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(true);

  const loadData = async () => {
    try {
      setLoading(true);
      setError(false);
      const response = await api.get<AccessCodePage>(
        `/auth/access-code?role=${accessCodeRole}`,
        {
          params: {
            page: currentPage - 1,
            size: accessCodesPerPage,
          },
        },
      );
      setAccessCodes(response.data.content);
      setTotalAccessCodes(response.data.totalElements);
      setPageCount(Math.max(1, response.data.totalPages));
    } catch {
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, [accessCodeRole]);

  const columns: AdminTableColumn<AccessCode>[] = [
    {
      key: "number",
      header: t("number"),
      render: (_accessCode, index) => `${index + 1}.`,
    },
    {
      key: "name",
      header: t("name"),
      render: (accessCode) => accessCode.name,
    },
    {
      key: "code",
      header: t("accessCode"),
      render: (accessCode) => accessCode.code,
    },
    {
      key: "active",
      header: t("used"),
      render: (accessCode) => (accessCode.used ? t("yes") : t("no")),
    },
    {
      key: "expiresAt",
      header: t("expiresAt"),
      render: (accessCode) =>
        new Date(accessCode.expiresAt).toLocaleString("pl-PL", {
          dateStyle: "short",

          timeStyle: "short",
        }),
    },
  ];

  const handleDelete = async (accessCode: AccessCode) => {
    if (!window.confirm(`${t("deleteAccessCodeConfirm")} ${accessCode.name}?`))
      return;
    try {
      await api.delete(`/auth/access-code/${accessCode.id}`);
      await loadData();
    } catch {
      setError(true);
    }
  };

  return (
    <section className={styles.page}>
      {loading && <LoadingOverlay />}
      {error && <ErrorMessage message={t("adminAccessCodesError")} />}

      <header className={styles.header}>
        <div className={styles.pageTitle}>{t("accessCodes")}</div>
        <div className={styles.roleButtons}>
          <button onClick={() => setAccessCodeRole("student")}>Students</button>
          <button onClick={() => setAccessCodeRole("teacher")}>Teachers</button>
          <button onClick={() => setAccessCodeRole("parent")}>Parents</button>
        </div>
      </header>

      <AdminTable
        items={accessCodes}
        columns={columns}
        loading={loading}
        emptyMessage={t("noAccessCodes")}
        actionsLabel={t("actions")}
        editLabel={t("edit")}
        deleteLabel={t("delete")}
        onDelete={(accessCode) => handleDelete(accessCode)}
      />

      {totalAccessCodes > 0 && (
        <PaginationFooter
          currentPage={currentPage}
          pageCount={pageCount}
          setCurrentPage={setCurrentPage}
        />
      )}
    </section>
  );
};

export default AdminAccessCodes;
