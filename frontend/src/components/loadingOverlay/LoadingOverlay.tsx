import styles from './LoadingOverlay.module.css';

export default function LoadingOverlay() {
  return (
    <div className={styles.loadingOverlay}>
      <div className={styles.spinner} />
    </div>
  );
}