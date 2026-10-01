import { Plus } from "lucide-react";
import styles from "./AdminAddButton.module.css";

type AdminAddButtonProps = {
  onClick: () => void;
  text: string;
};

const AdminAddButton = ({
  onClick,
  text,
}: AdminAddButtonProps) => {
  return (
    <button
      className={styles.button}
      type="button"
      onClick={onClick}
    >
      <Plus size={18} />
      {text}
    </button>
  );
};

export default AdminAddButton;
