import type { Assessment, AssessmentType } from "@types-local/index";
import styles from "./DashboardAssessmentItem.module.css";
import { useState } from "react";
import AssessmentModal from "@components/assessment/assessmentModal/AssessmentModal";

const typeClass: Record<AssessmentType, string> = {
  TEST: styles.test,
  QUIZ: styles.quiz,
  CLASS_TEST: styles.classTest,
  HOMEWORK: styles.homework,
};

const DashboardAssessmentItem = ({
  assessment,
}: {
  assessment: Assessment;
}) => {
  const [selectedAssessment, setSelectedAssessment] =
    useState<Assessment | null>(null);

  return (
    <>
      <div
        className={styles.assessment}
        onClick={() => setSelectedAssessment(assessment)}
      >
        <div>
          <div className={styles.subject}>{assessment.subjectName}</div>

          <span className={`${styles.type} ${typeClass[assessment.type]}`}>
            {assessment.type.replace("_", " ")}
          </span>
        </div>

        <div className={styles.assessmentDate}>
          {new Date(assessment.dateDue).toLocaleDateString("pl-PL")}
        </div>
      </div>
      
      {selectedAssessment && (
        <AssessmentModal
          selectedAssessment={selectedAssessment}
          setSelectedAssessment={setSelectedAssessment}
        />
      )}
    </>
  );
};

export default DashboardAssessmentItem;
