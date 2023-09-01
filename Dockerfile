FROM openjdk:17-oracle

ENV APP_HOME=/weather-diary

LABEL description="Weather Diary Application"

EXPOSE 8080

RUN git clone https://github.com/hgleeee/weather-diary.git

WORKDIR $APP_HOME

RUN chmod 700 ./gradlew # gradlew 실행 권한 부여
RUN ./gradlew bootJar
RUN mv build/libs/*.jar app.jar

ENTRYPOINT [ "java", "-jar", "/app.jar" ]