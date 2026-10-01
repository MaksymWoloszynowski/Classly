import type { ReactNode } from "react";
import { Edit3, Link2, Trash2 } from "lucide-react";
import styles from "./AdminTable.module.css";

type AdminTableColumn<T> = {
  key: string;
  header: ReactNode;
  render: (item: T, index: number) => ReactNode;
  className?: string;
};

type AdminTableProps<T extends { id: string }> = {
  items: T[];
  columns: AdminTableColumn<T>[];
  loading: boolean;
  emptyMessage: string;
  actionsLabel: string;
  editLabel: string;
  deleteLabel: string;
  linkLabel?: string;
  onEdit?: (item: T) => void;
  onDelete: (item: T) => void;
  onLink?: (item: T) => void;
  onRowClick?: (item: T) => void;
};

const AdminTable = <T extends { id: string }>({
  items,
  columns,
  loading,
  emptyMessage,
  actionsLabel,
  editLabel,
  deleteLabel,
  linkLabel,
  onEdit,
  onDelete,
  onLink,
    onRowClick,
}: AdminTableProps<T>) => {
  return (
    <div className={styles.tableShell}>
      <table>
        <thead>
          <tr>
            {columns.map((column) => (
              <th className={column.className} key={column.key}>
                {column.header}
              </th>
            ))}
            <th className={styles.actionsHeading}>{actionsLabel}</th>
          </tr>
        </thead>
        <tbody>
          {items.map((item, index) => (
            <tr key={item.id} onClick={() => onRowClick && onRowClick(item)}>
              {columns.map((column) => (
                <td className={column.className} key={column.key}>
                  {column.render(item, index)}
                </td>
              ))}
              <td className={styles.actionsCell}>
                {onLink && linkLabel && (
                  <button
                    className={styles.iconButton}
                    type="button"
                    onClick={(event) => {
                      event.stopPropagation();
                      onLink(item);
                    }}
                    aria-label={linkLabel}
                    title={linkLabel}
                  >
                    <Link2 size={17} />
                  </button>
                )}
                {onEdit && (
                    <button
                    className={styles.iconButton}
                    type="button"
                    onClick={(event) => {
                        event.stopPropagation();
                        onEdit(item);
                    }}
                    aria-label={editLabel}
                    title={editLabel}
                    >
                    <Edit3 size={17} />
                    </button>
                )}
                <button
                  className={`${styles.iconButton} ${styles.deleteButton}`}
                  type="button"
                  onClick={(event) => {
                    event.stopPropagation();
                    onDelete(item);
                  }}
                  aria-label={deleteLabel}
                  title={deleteLabel}
                >
                  <Trash2 size={17} />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {!loading && items.length === 0 && (
        <p className={styles.empty}>{emptyMessage}</p>
      )}
    </div>
  );
};

export default AdminTable;
export type { AdminTableColumn };
