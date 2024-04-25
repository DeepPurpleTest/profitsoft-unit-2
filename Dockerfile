FROM maven:3.8.4 as build
ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME
COPY pom.xml $HOME
RUN mvn verify --fail-never
COPY . $HOME
RUN mvn clean package

FROM eclipse-temurin:17-alpine

COPY --from=build /app/target/profitsoft-unit-2-0.0.1-SNAPSHOT.jar /app/runner.jar

ENTRYPOINT java -jar /app/runner.jar
