FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} apigateway.jar

ENTRYPOINT ["java", "-jar", "/apigateway.jar"]

EXPOSE 9090


####  docker run -d -p 9090:9090 -e CONFIG_SERVER_URL=http://host.docker.internal -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name apigateway fd2f2601dfd5