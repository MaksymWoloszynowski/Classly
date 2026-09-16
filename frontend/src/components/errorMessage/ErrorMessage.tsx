import styles from "./ErrorMessage.module.css";
import { useTranslation } from "../../hooks/useTranslation";

type ErrorMessageProps = {
  message?: string;
  onRetry?: () => void;
};

const ErrorMessage = ({
  message,
  onRetry,
}: ErrorMessageProps) => {
  const { t } = useTranslation();

  return (
    <div className={styles.error} role="alert">
      <span>{message ?? t("genericError")}</span>
      {onRetry && (
        <button type="button" onClick={onRetry}>
          {t("tryAgain")}
        </button>
      )}
    </div>
  );
};

export default ErrorMessage;
