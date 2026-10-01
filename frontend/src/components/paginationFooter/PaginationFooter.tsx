import styles from "./PaginationFooter.module.css";
import { ChevronLeft, ChevronRight } from "lucide-react";
import { useTranslation } from "../../hooks/useTranslation";

type PaginationFooterProps = {
  currentPage: number;
  pageCount: number;
  setCurrentPage: (page: number) => void;
};

const PaginationFooter = ({
  currentPage,
  pageCount,
  setCurrentPage,
}: PaginationFooterProps) => {
    const { t } = useTranslation();
  return (
    <div className={styles.pagination}>
      <button
        className={styles.paginationButton}
        type="button"
        onClick={() => setCurrentPage(Math.max(1, currentPage - 1))}
        disabled={currentPage === 1}
        aria-label={t("previousPage")}
        title={t("previousPage")}
      >
        <ChevronLeft size={18} />
      </button>
      <span>
        {t("page")} {currentPage} / {pageCount}
      </span>
      <button
        className={styles.paginationButton}
        type="button"
        onClick={() => setCurrentPage(Math.min(pageCount, currentPage + 1))}
        disabled={currentPage === pageCount}
        aria-label={t("nextPage")}
        title={t("nextPage")}
      >
        <ChevronRight size={18} />
      </button>
    </div>
  );
};

export default PaginationFooter;
