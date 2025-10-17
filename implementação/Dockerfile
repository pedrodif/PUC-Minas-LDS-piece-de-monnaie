FROM eclipse-temurin:21-jre
LABEL authors="pedro"

ARG JAR_FILE=target/piece-de-monnaie-*.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]