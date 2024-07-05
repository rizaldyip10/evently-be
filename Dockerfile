FROM maven:3.9.7-sapmachine-22 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:22-slim

WORKDIR /app

COPY --from=build /app/target/minproBE.jar /app/

ENTRYPOINT ["java", "-jar", "/app/minproBE.jar"]

