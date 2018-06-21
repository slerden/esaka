FROM openjdk:8-jre-alpine
COPY target/esaka-filestore-0.0.1-SNAPSHOT.jar /usr/src/esaka/
WORKDIR /usr/src/esaka
EXPOSE 8071
CMD ["java", "-jar", "esaka-filestore-0.0.1-SNAPSHOT.jar"]