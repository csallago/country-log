FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/country-log*SNAPSHOT.jar
COPY ${JAR_FILE} country-log-app.jar
ENTRYPOINT ["java","-jar","-Dspring.redis.host=rediscontainer","/country-log-app.jar"]