FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

COPY common/pom.xml common/pom.xml
COPY common/src common/src

COPY schedule-service/pom.xml schedule-service/pom.xml
COPY schedule-service/src schedule-service/src

RUN mvn install -N -DskipTests -B

RUN mvn -f common/pom.xml install -DskipTests -B

RUN mvn -f schedule-service/pom.xml package -DskipTests -B

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/schedule-service/target/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]