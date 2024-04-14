FROM openjdk:8-alpine

ARG JAR_FILE=build/libs/back-worker-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
