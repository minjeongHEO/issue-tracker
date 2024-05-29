FROM amazoncorretto:17
EXPOSE 8080
EXPOSE 443
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/ssl/keystore.p12 /app/resources/ssl/keystore.p12
ENTRYPOINT ["java","-jar","/app.jar"]
