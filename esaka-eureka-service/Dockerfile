#FROM openjdk:8
#VOLUME /tmp
#ARG JAR_FILE
#ARG EXPOSE_PORT
#ADD ${JAR_FILE} app.jar
#EXPOSE ${EXPOSE_PORT}
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

FROM openjdk:8-jre-alpine
COPY target/esaka-eureka-service-0.0.1-SNAPSHOT.jar /usr/src/esaka/
WORKDIR /usr/src/esaka
EXPOSE 8070
CMD ["java", "-jar", "esaka-eureka-service-0.0.1-SNAPSHOT.jar"]