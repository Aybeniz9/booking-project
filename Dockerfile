FROM openjdk:21
COPY target/turing-booking-project-1.0-SNAPSHOT.jar turing-booking-project.jar
ENTRYPOINT ["java", "-jar", "turing-booking-project.jar"]