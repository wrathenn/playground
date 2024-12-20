FROM gradle:7.6.0-jdk17-jammy AS build

WORKDIR /home/gradle/backend

COPY settings.gradle.kts ./
COPY build.gradle.kts ./
COPY gradle.properties ./
COPY src ./

RUN gradle :bootJar --no-daemon

FROM eclipse-temurin:17-jre-jammy

ENV LANG en_US.UTF-8
EXPOSE 8080

COPY --from=build /home/gradle/backend/build/libs/${PROJECT}-*-all.jar /app/app.jar
#CMD tail -f /dev/null
ENTRYPOINT ["java", "-jar", "-Djava.awt.headless=true", "-Dspring.profiles.active=prod", "/app/app.jar"]