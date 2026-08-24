import { useEffect, useRef, useState } from "react";
import styles from "./Login.module.css";
import api from "../../api/api";
import useAuth from "../../hooks/useAuth";
import { useNavigate } from "react-router-dom";
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

      me.data.role === "ROLE_STUDENT"
        ? navigate("/home")
        : navigate("/dashboard");
    } catch (error) {
      console.error("Error during login:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      {loading} && <LoadingOverlay />
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
    </div>
  );
};

export default Login;
