import { useRef, useState, useEffect } from "react";
import api from "../../api/api.js";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styles from "./Register.module.css";
import useAuth from "../../hooks/useAuth.js";
import axios from "axios";

const PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,128}$/;

const Register = () => {
  const { setAuth } = useAuth();
  const navigate = useNavigate();
  const emailRef = useRef<HTMLInputElement | null>(null);

  const [accessCode, setAccessCode] = useState("");
  const [accessCodeFocus, setAccessCodeFocus] = useState(false);

  const [email, setEmail] = useState("");
  const validEmail = email.trim() !== "";
  const [emailFocus, setEmailFocus] = useState(false);

  const [password, setPassword] = useState("");
  const validPassword = PASSWORD_REGEX.test(password);
  const [passwordFocus, setPasswordFocus] = useState(false);

  const [matchPassword, setMatchPassword] = useState("");
  const validMatch = password === matchPassword;
  const [matchFocus, setMatchFocus] = useState(false);

  const [errMsg, setErrMsg] = useState("");

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
      navigate("/home");
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        setErrMsg(err.response?.data?.message ?? "Registration failed.");
      } else {
        setErrMsg("Registration failed.");
      }
    }
  };

  return (
    <section className={styles.page}>
      <div className={styles.container}>
        <p className={errMsg ? styles.error : styles.invisible}>{errMsg}</p>
        <h1 className={styles.title}>Create an account</h1>
        <form className={styles.form} onSubmit={handleSubmit}>
          <label className={styles.label} htmlFor="email">
            Email:
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
            onFocus={() => setEmailFocus(true)}
            onBlur={() => setEmailFocus(false)}
            className={`${styles.input} ${
              email
                ? validEmail
                  ? styles.valid_input
                  : styles.invalid_input
                : ""
            }`}
          />

          <label className={styles.label} htmlFor="password">
            Password:
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
            Confirm Password:
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
            Access Code:
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
            onFocus={() => setAccessCodeFocus(true)}
            onBlur={() => setAccessCodeFocus(false)}
            className={styles.input}
          />

          <button className={styles.button}>Sign Up</button>
        </form>
        <div className={styles.footer_text}>
          <Link to="/login">Sign In</Link>
        </div>
      </div>
    </section>
  );
};

export default Register;
