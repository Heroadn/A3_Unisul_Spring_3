FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp

ARG DEPENDENCY=build
RUN echo ${DEPENDENCY}
COPY ${DEPENDENCY}/libs/demo-0.0.1-SNAPSHOT.jar /app/libs/demo.jar
ENTRYPOINT ["java","-jar","/app/libs/demo.jar"]