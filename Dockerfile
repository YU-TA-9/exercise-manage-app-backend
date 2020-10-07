FROM amazoncorretto:11
ARG JAR_FILE
COPY ${JAR_FILE} target/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","target/app.jar","--spring.profiles.active=production"]