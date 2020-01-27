FROM openjdk:8-jdk as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw package -DskipTests

FROM openjdk:8-jre-alpine
ARG RESOURCES=/var/datamcbaseface/
ARG TARGET=/workspace/app/target/
COPY --from=build ${TARGET}/bloomdex-datamcbaseface.jar /app/bloomdex-datamcbaseface.jar
WORKDIR /app/
EXPOSE 25565/tcp
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-Dspring.config.location=/var/datamcbaseface/application.properties", "-jar", "bloomdex-datamcbaseface.jar"]
