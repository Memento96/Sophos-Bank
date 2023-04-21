FROM openjdk:11-jdk
EXPOSE 8080
ARG JAR_FILE=target/bank-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} bank-api.jar
ENTRYPOINT ["sh", "-c", "java -jar /bank-api.jar"]
