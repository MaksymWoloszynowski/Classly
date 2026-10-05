FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

COPY common/pom.xml common/pom.xml
COPY common/src common/src

COPY teaching-service/pom.xml teaching-service/pom.xml
COPY teaching-service/src teaching-service/src

RUN mvn install -N -DskipTests -B

RUN mvn -f common/pom.xml install -DskipTests -B

RUN mvn -f teaching-service/pom.xml package -DskipTests -B

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/teaching-service/target/*.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]