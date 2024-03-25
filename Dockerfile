FROM openjdk:8-alpine

WORKDIR /workspace

ARG JAR_PATH=./build/libs

COPY ${JAR_PATH}/back-worker-0.0.1-SNAPSHOT.jar ${JAR_PATH}/back-worker-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "./build/libs/back-worker-0.0.1-SNAPSHOT.jar"]
