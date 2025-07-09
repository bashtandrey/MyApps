FROM openjdk:21-jdk-slim

VOLUME /tmp

COPY target/MyApps-1.0.0.war app.war

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/app.war"]
