FROM gradle:7.6-jdk17-alpine as build
ENV APP_HOME=/weather-diary
LABEL description="Weather Diary Application"

# 필수 실행 파일 복사
WORKDIR $APP_HOME
COPY build.gradle $APP_HOME
COPY settings.gradle $APP_HOME
COPY gradlew $APP_HOME
COPY gradle $APP_HOME/gradle

RUN chmod +x gradlew
RUN ./gradlew -x test build || return 0
COPY src $APP_HOME/src
RUN ./gradlew clean build

# jar만 옮겨서
FROM openjdk:17.0.2
ENV APP_HOME=/weather-diary
WORKDIR $APP_HOME

COPY --from=build $APP_HOME/build/libs/weather-diary-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/app.jar" ]