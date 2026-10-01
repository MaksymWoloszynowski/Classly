docker exec -i school-structure-service-db sh -c 'psql -U "$(cat /run/secrets/DB_USER)" -d school-structure-service-db' < 01_school_structure.sql
docker exec -i schedule-service-db sh -c 'psql -U "$(cat /run/secrets/DB_USER)" -d schedule-service-db' < 02_schedule.sql
docker exec -i grade-service-db sh -c 'psql -U "$(cat /run/secrets/DB_USER)" -d grade-service-db' < 03_grade.sql
docker exec -i teaching-service-db sh -c 'psql -U "$(cat /run/secrets/DB_USER)" -d teaching-service-db' < 04_teaching.sql

