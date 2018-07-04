FROM openjdk:8-jre-alpine
COPY target/esaka-auth-0.0.1-SNAPSHOT.jar /usr/src/esaka/
WORKDIR /usr/src/esaka
EXPOSE 8072
EXPOSE 7072
CMD ["java", "-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,address=7072,suspend=y", "-jar", "esaka-auth-0.0.1-SNAPSHOT.jar"]