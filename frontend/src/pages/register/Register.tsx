import { useRef, useState, useEffect } from "react";
import api from "../../api/api.js";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styles from "./Register.module.css";
import useAuth from "../../hooks/useAuth.js";
import axios from "axios";
import ErrorMessage from "../../components/errorMessage/ErrorMessage";
import useLocalePath from "../../hooks/useLocalePath";
import { useTranslation } from "../../hooks/useTranslation.js";
import TopBar from "../../components/topBar/TopBar";
import LoadingOverlay from "@components/loadingOverlay/LoadingOverlay.js";

const PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,128}$/;

const Register = () => {
  const { setAuth } = useAuth();
  const navigate = useNavigate();
  const localePath = useLocalePath();
  const [loading, setLoading] = useState(false);
  const emailRef = useRef<HTMLInputElement | null>(null);

  const [accessCode, setAccessCode] = useState("");

  const [email, setEmail] = useState("");
  const validEmail = email.trim() !== "";

  const [password, setPassword] = useState("");
  const validPassword = PASSWORD_REGEX.test(password);
  const [passwordFocus, setPasswordFocus] = useState(false);

  const [matchPassword, setMatchPassword] = useState("");
  const validMatch = password === matchPassword;
  const [matchFocus, setMatchFocus] = useState(false);

  const [errMsg, setErrMsg] = useState("");
  const { t } = useTranslation();

  useEffect(() => {
    emailRef.current?.focus();
  }, []);

  useEffect(() => {
    setErrMsg("");
  }, [email, password, matchPassword, accessCode]);

  const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    const isAccessCodeValid = accessCode.trim().length > 0;

    if (!validEmail || !validPassword || !validMatch || !isAccessCodeValid) {
      setErrMsg("Please correct the highlighted fields.");

      return;
    }
    try {
      setLoading(true);
      await api.post("/auth/register", {
        email,
        password,
        accessCode,
      });
      const me = await api.get("/api/my-profile");
      setAuth(me.data.user);

      setPassword("");
      setMatchPassword("");
      setAccessCode("");
      navigate(localePath("/home"));
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        setErrMsg(err.response?.data?.message ?? "Registration failed.");
      } else {
        setErrMsg("Registration failed.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
    {loading && <LoadingOverlay />}
    <section className={styles.page}>
      <TopBar />
      <div className={styles.formArea}>
        <div className={styles.container}>
          {errMsg && <ErrorMessage message={errMsg} />}
          <h1 className={styles.title}>{t("register")}</h1>
          <form className={styles.form} onSubmit={handleSubmit}>
          <label className={styles.label} htmlFor="email">
            {t("email")}:
            <span className={styles.requiredAsterisk}> *</span>
          </label>
          <input
            type="email"
            id="email"
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setEmail(e.target.value)
            }
            value={email}
            required
            ref={emailRef}
            className={`${styles.input} ${
              email
                ? validEmail
                  ? styles.valid_input
                  : styles.invalid_input
                : ""
            }`}
          />

          <label className={styles.label} htmlFor="password">
            {t("password")}:
            <span className={styles.requiredAsterisk}> *</span>
          </label>
          <input
            type="password"
            id="password"
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setPassword(e.target.value)
            }
            value={password}
            required
            aria-invalid={validPassword ? "false" : "true"}
            onFocus={() => setPasswordFocus(true)}
            onBlur={() => setPasswordFocus(false)}
            className={`${styles.input} ${
              password
                ? validPassword
                  ? styles.valid_input
                  : styles.invalid_input
                : ""
            }`}
          />
          <p
            id="pwdnote"
            className={
              passwordFocus && !validPassword
                ? styles.instructions
                : styles.invisible
            }
          >
            8–128 characters, include uppercase, lowercase and a number.
          </p>

          <label className={styles.label} htmlFor="confirm_pwd">
            {t("confirmPassword")}:
            <span className={styles.requiredAsterisk}> *</span>
          </label>
          <input
            type="password"
            id="confirm_pwd"
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setMatchPassword(e.target.value)
            }
            value={matchPassword}
            required
            aria-invalid={validMatch ? "false" : "true"}
            onFocus={() => setMatchFocus(true)}
            onBlur={() => setMatchFocus(false)}
            className={`${styles.input} ${
              matchPassword
                ? validMatch
                  ? styles.valid_input
                  : styles.invalid_input
                : ""
            }`}
          />
          <p
            id="confirmnote"
            className={
              matchFocus && !validMatch ? styles.instructions : styles.invisible
            }
          >
            Must match the first password input field.
          </p>

          <label className={styles.label} htmlFor="access_code">
            {t("accessCode")}:
            <span className={styles.requiredAsterisk}> *</span>
          </label>
          <input
            type="text"
            id="access_code"
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setAccessCode(e.target.value)
            }
            value={accessCode}
            required
            className={styles.input}
          />

          <button className={styles.button}>Sign Up</button>
          </form>
          <div className={styles.footerText}>
            <Link to={localePath("/login")}>{t("signIn")}</Link>
          </div>
        </div>
      </div>
    </section>
    </>
  );
};

export default Register;
