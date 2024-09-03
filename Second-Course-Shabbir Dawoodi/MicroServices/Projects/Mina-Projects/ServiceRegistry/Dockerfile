FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} serviceregistery.jar

ENTRYPOINT ["java", "-jar", "/serviceregistery.jar"]

EXPOSE 8082



## docker run --name serviceregistery -p8761:8761 e1ced32 -d