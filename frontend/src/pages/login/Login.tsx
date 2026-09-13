import { useEffect, useRef, useState } from "react";
import styles from "./Login.module.css";
import api from "../../api/api";
import useAuth from "../../hooks/useAuth";
import { Link, useNavigate } from "react-router-dom";
import LoadingOverlay from "../../components/loadingOverlay/LoadingOverlay";

const Login = () => {
  const { setAuth } = useAuth();
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const emailRef = useRef<HTMLInputElement>(null);
  const navigate = useNavigate();
  const [loading, setLoading] = useState<boolean>(false);

  useEffect(() => {
    emailRef.current?.focus();
  }, []);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await api.post("/auth/login", { email, password });
      setLoading(true);
      const me = await api.get("/api/my-profile");
      setAuth(me.data);

      navigate(
        me.data.role === "ROLE_TEACHER" ? "/teacher/home" : "/student/home",
      );
    } catch (error) {
      console.error("Error during login:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className={styles.page}>
      {loading && <LoadingOverlay />}  
      <div className={styles.container}>
        <h1 className={styles.title}>Sign In</h1>
        <form className={styles.form} onSubmit={handleSubmit}>
          <label className={styles.label} htmlFor="email">
            Email:
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
            Password:
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

        <div className={styles.footer_text}>
          <Link to="/register">Create an account</Link>
        </div>
      </div>
    </section>
  );
};

export default Login;
