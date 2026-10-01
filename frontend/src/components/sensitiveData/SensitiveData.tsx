import { useState } from "react";
import styles from "./SensitiveData.module.css";
import { useTranslation } from "@hooks/useTranslation";

const SensitiveData = ({ value }: { value: string }) => {
  const [visible, setVisible] = useState(false);
  const {t} = useTranslation();

  return (
    <span>
      {visible ? value : "•••••••••••"}{" "}
      <button onClick={() => setVisible(!visible)}> 
        {visible ? t("hide") : t("show")}
      </button>
    </span>
  );
}

export default SensitiveData;