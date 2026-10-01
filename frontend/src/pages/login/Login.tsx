import { useEffect, useRef, useState } from "react";
import styles from "./Login.module.css";
import api from "../../api/api";
import useAuth from "../../hooks/useAuth";
import { Link, useNavigate } from "react-router-dom";
import LoadingOverlay from "../../components/loadingOverlay/LoadingOverlay";
import ErrorMessage from "../../components/errorMessage/ErrorMessage";
import axios from "axios";
import useLocalePath from "../../hooks/useLocalePath";
import { useTranslation } from "../../hooks/useTranslation";
import TopBar from "../../components/topBar/TopBar";

const Login = () => {
  const { setAuth } = useAuth();
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const emailRef = useRef<HTMLInputElement>(null);
  const navigate = useNavigate();
  const localePath = useLocalePath();
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const { t } = useTranslation();

  useEffect(() => {
    emailRef.current?.focus();
  }, []);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      await api.post("/auth/login", { email, password });
      const me = await api.get("/api/my-profile");
      setAuth(me.data);

      switch (me.data.role) {
        case "ROLE_TEACHER":
          navigate(localePath("/teacher/dashboard"));
          break;
        case "ROLE_STUDENT":
        case "ROLE_PARENT":
          navigate(localePath("/student/dashboard"));
          break;
        case "ROLE_ADMIN":
          navigate(localePath("/admin/dashboard"));
          break;
        default:
          navigate(localePath("/"));
      }
    } catch (error) {
      console.error("Error during login:", error);
      setError(
        axios.isAxiosError(error)
          ? (error.response?.data?.message ?? t("loginFailed"))
          : "Login failed.",
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className={styles.page}>
      <TopBar />
      <div className={styles.formArea}>
        {loading && <LoadingOverlay />}
        <div className={styles.container}>
          {error && <ErrorMessage message={error} />}
          <h1 className={styles.title}>{t("login")}</h1>
          <form className={styles.form} onSubmit={handleSubmit}>
          <label className={styles.label} htmlFor="email">
            {t("email")}:
          </label>
          <input
            type="email"
            id="email"
            ref={emailRef}
            onChange={(e) => setEmail(e.target.value)}
            value={email}
            required
            className={styles.input}
          />

          <label className={styles.label} htmlFor="password">
            {t("password")}:
          </label>
          <input
            type="password"
            id="password"
            onChange={(e) => setPassword(e.target.value)}
            value={password}
            required
            className={styles.input}
          />
          <button className={styles.button}>Sign In</button>
          </form>

          <div className={styles.footerText}>
            <Link to={localePath("/register")}>{t("register")}</Link>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Login;
