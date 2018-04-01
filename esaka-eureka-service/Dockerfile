FROM openjdk:8
VOLUME /tmp
ARG JAR_FILE
ARG EXPOSE_PORT
ADD ${JAR_FILE} app.jar
EXPOSE ${EXPOSE_PORT}
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]