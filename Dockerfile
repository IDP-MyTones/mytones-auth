FROM openjdk:17

COPY target/mytones-auth.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]