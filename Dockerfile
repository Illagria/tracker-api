FROM openjdk:11.0.10-oraclelinux7

USER 1001

COPY build/libs/trackerApi-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "/app/trackerApi-0.0.1-SNAPSHOT.jar"]
