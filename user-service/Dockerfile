FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

COPY common/pom.xml common/pom.xml
COPY user-service/pom.xml user-service/pom.xml

COPY common/src common/src
RUN mvn -B -f common/pom.xml clean install -DskipTests

RUN mvn -B -f user-service/pom.xml dependency:go-offline

COPY user-service/src user-service/src
RUN mvn -B -f user-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
RUN addgroup -S app && adduser -S app -G app
USER app
WORKDIR /app

COPY --from=build /build/user-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]