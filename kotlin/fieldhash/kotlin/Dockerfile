FROM gradle:8.9.0-jdk17-jammy AS build

WORKDIR /home/gradle/kotlin

COPY build.gradle.kts ./
COPY settings.gradle.kts ./
COPY gradle.properties ./

COPY api-model/src ./api-model/src
COPY api-model/build.gradle.kts ./api-model/

COPY shared/src ./shared/src
COPY shared/build.gradle.kts ./shared/

COPY backend/src ./backend/src
COPY backend/build.gradle.kts ./backend/

RUN gradle :backend:bootJar --no-daemon

#ENTRYPOINT ["tail", "-f", "/dev/null"]
FROM eclipse-temurin:17-jre-jammy

ENV LANG en_US.UTF-8
EXPOSE 8080

COPY --from=build /home/gradle/kotlin/backend/build/libs/backend-*-all.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "-Djava.awt.headless=true", "-Dspring.profiles.active=prod", "/app/app.jar"]