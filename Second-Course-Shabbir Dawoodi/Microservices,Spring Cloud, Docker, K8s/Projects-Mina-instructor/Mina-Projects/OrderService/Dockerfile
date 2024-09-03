FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} orderservice.jar

ENTRYPOINT ["java", "-jar", "/orderservice.jar"]

EXPOSE 8081


### docker run -d -p 8081:8081 -e CONFIG_SERVER_URL=http://host.docker.internal -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name orderservice 97a1514ace0e

