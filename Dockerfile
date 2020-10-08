FROM amazoncorretto:11
ARG JAR_FILE
COPY ${JAR_FILE} /app.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=production"]