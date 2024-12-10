FROM amazoncorretto:21-alpine-jdk
COPY target/*.jar lm-backend.jar
ENTRYPOINT ["java", "-jar", "/lm-backend.jar"]