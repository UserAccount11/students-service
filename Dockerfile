ARG APP_NAME=students-service

FROM openjdk:21 AS stage1

ARG APP_NAME

WORKDIR /app/$APP_NAME

COPY ./.mvn ./.mvn
COPY ./mvnw .
COPY ./pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:21

ARG APP_NAME

WORKDIR /app

COPY --from=stage1 /app/$APP_NAME/target/students-service-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

CMD ["java", "-jar", "students-service-0.0.1-SNAPSHOT.jar"]