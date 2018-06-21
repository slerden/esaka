FROM openjdk:8-jre-alpine
COPY target/esaka-gateway-api-0.0.1-SNAPSHOT.jar /usr/src/esaka/
WORKDIR /usr/src/esaka
EXPOSE 8079
CMD ["java", "-jar", "esaka-gateway-api-0.0.1-SNAPSHOT.jar"]