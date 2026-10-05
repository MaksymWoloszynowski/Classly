import { useTranslation } from "@hooks/useTranslation";
import styles from "./FormFooterButtons.module.css";

type FormFooterButtonsProps = {
  onClose: () => void;
  saving: boolean;
  saveText: string;
};

const FormFooterButtons = ({ onClose, saving, saveText }: FormFooterButtonsProps) => {
  const { t } = useTranslation();

  return (
    <div className={styles.actions}>
      <button
        type="button"
        className={styles.cancel}
        onClick={onClose}
        disabled={saving}
      >
        {t("cancel")}
      </button>

      <button type="submit" className={styles.submit} disabled={saving}>
        {saving
          ? "Saving..."
          : saveText}
      </button>
    </div>
  );
};

export default FormFooterButtons;
