FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

COPY common/pom.xml common/pom.xml
COPY common/src common/src

COPY grade-service/pom.xml grade-service/pom.xml
COPY grade-service/src grade-service/src

RUN mvn install -N -DskipTests -B

RUN mvn -f common/pom.xml install -DskipTests -B

RUN mvn -f grade-service/pom.xml package -DskipTests -B

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/grade-service/target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]