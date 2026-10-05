FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

COPY common/pom.xml common/pom.xml
COPY common/src common/src

COPY school-structure-service/pom.xml school-structure-service/pom.xml
COPY school-structure-service/src school-structure-service/src

RUN --mount=type=cache,target=/root/.m2 \
	mvn install -N -DskipTests -B \
	-Daether.connector.basic.retryCount=5 \
	-Dmaven.wagon.http.retryHandler.count=5

RUN --mount=type=cache,target=/root/.m2 \
	mvn -f common/pom.xml install -DskipTests -B \
	-Daether.connector.basic.retryCount=5 \
	-Dmaven.wagon.http.retryHandler.count=5

RUN --mount=type=cache,target=/root/.m2 \
	mvn -f school-structure-service/pom.xml package -DskipTests -B \
	-Daether.connector.basic.retryCount=5 \
	-Dmaven.wagon.http.retryHandler.count=5

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/school-structure-service/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]