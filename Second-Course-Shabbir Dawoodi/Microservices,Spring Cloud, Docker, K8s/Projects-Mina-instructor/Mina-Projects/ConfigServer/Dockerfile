FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} configserver.jar

ENTRYPOINT ["java", "-jar", "/configserver.jar"]

EXPOSE 9296


####  docker run -d -p9296:9296 -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name configserver 30566