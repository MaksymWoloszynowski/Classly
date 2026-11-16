FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

COPY common/pom.xml common/pom.xml
COPY common/src common/src

COPY schedule-service/pom.xml schedule-service/pom.xml
COPY schedule-service/src schedule-service/src

RUN mvn install -N -DskipTests -B

RUN mvn -f common/pom.xml install -DskipTests -B

RUN mvn -f schedule-service/pom.xml package -DskipTests -B

FROM eclipse-temurin:21-jre

WORKDIR /app

RUN useradd -r -u 10001 app

COPY --from=build /app/schedule-service/target/*.jar app.jar

USER app

EXPOSE 8083

HEALTHCHECK --interval=30s --timeout=5s --start-period=120s --retries=3 \
  CMD curl -fs http://localhost:9091/actuator/health/readiness || exit 1

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]