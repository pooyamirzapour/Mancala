FROM maven:3-openjdk-11 as build
WORKDIR /app

COPY pom.xml .
COPY src/ src/
RUN mvn package

FROM adoptopenjdk/openjdk11
COPY --from=build /app/target/Mancala-0.0.1-SNAPSHOT.jar /app/Mancala-0.0.1.jar
EXPOSE 8087
ENTRYPOINT ["java","-jar","/app/Mancala-0.0.1.jar"]

