import { useEffect, useState } from "react";
import {
  BookOpen,
  CalendarCog,
  GraduationCap,
  Link2,
  UserRound,
  Users,
  UsersRound,
} from "lucide-react";
import { Link } from "react-router-dom";
import api from "../../../api/api";
import ErrorMessage from "../../../components/errorMessage/ErrorMessage";
import LoadingOverlay from "../../../components/loadingOverlay/LoadingOverlay";
import useLocalePath from "../../../hooks/useLocalePath";
import { useTranslation } from "../../../hooks/useTranslation";
import styles from "../Dashboard.module.css";

type AdminStatistics = {
  students: number;
  teachers: number;
  parents: number;
  groups: number;
  subjects: number;
  teachingAssignments: number;
  classificationPeriods: number;
};

const initialStatistics: AdminStatistics = {
  students: 0,
  teachers: 0,
  parents: 0,
  groups: 0,
  subjects: 0,
  teachingAssignments: 0,
  classificationPeriods: 0,
};

const AdminDashboard = () => {
  const { t } = useTranslation();
  const localePath = useLocalePath();
  const [statistics, setStatistics] = useState(initialStatistics);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);

  const loadStatistics = async () => {
    try {
      setLoading(true);
      setError(false);
      const response = await api.get<AdminStatistics>("/api/statistics/");
      setStatistics(response.data);
    } catch {
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadStatistics();
  }, []);

  const cards = [
    ["totalStudents", statistics.students, GraduationCap],
    ["totalTeachers", statistics.teachers, UserRound],
    ["totalParents", statistics.parents, Users],
    ["totalGroups", statistics.groups, UsersRound],
    ["totalSubjects", statistics.subjects, BookOpen],
    ["teachingAssignments", statistics.teachingAssignments, Link2],
    [
      "totalClassificationPeriods",
      statistics.classificationPeriods,
      CalendarCog,
    ],
  ] as const;

  return (
    <>
      {loading && <LoadingOverlay />}
      {error && <ErrorMessage message={t("adminStatisticsError")} />}

      <div className={styles.pageTitle}>{t("dashboard")}</div>

      <div className={styles.adminDashboard}>
        <div>
          <div className={styles.sectionHeader}>{t("stats")}</div>
          <div className={styles.statsGrid}>
            {cards.map(([label, value, Icon]) => (
              <article className={styles.statCard} key={label}>
                <div className={styles.statIcon}>
                  <Icon size={21} strokeWidth={1.8} />
                </div>
                <div>
                  <span className={styles.statValue}>{value}</span>
                  <span>
                    {t(
                      label as
                        | "totalStudents"
                        | "totalTeachers"
                        | "totalParents"
                        | "totalGroups"
                        | "totalSubjects"
                        | "teachingAssignments"
                        | "totalClassificationPeriods",
                    )}
                  </span>
                </div>
              </article>
            ))}
          </div>
        </div>

        <div className={styles.actionsSection}>
          <div className={styles.sectionHeader}>{t("quickActions")}</div>
          <div className={styles.actionGrid}>
            <Link className={styles.action} to={localePath("/admin/students")}>
              <UserRound size={20} />
              <p>{t("addStudent")}</p>
            </Link>
            <Link className={styles.action} to={localePath("/admin/teachers")}>
              <UserRound size={20} />
              <p>{t("addTeacher")}</p>
            </Link>
            <Link className={styles.action} to={localePath("/admin/groups")}>
              <Users size={20} />
              <p>{t("manageGroups")}</p>
            </Link>
            <Link className={styles.action} to={localePath("/admin/schedule")}>
              <CalendarCog size={20} />
              <p>{t("schedule")}</p>
            </Link>
          </div>
        </div>
      </div>
    </>
  );
};

export default AdminDashboard;
