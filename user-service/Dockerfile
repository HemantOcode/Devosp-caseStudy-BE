# ---------- Build Stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

COPY common/pom.xml common/pom.xml
COPY user-service/pom.xml user-service/pom.xml

RUN mvn -B -q -f user-service/pom.xml dependency:go-offline

COPY common/src common/src
COPY user-service/src user-service/src

RUN mvn -B -q -f common/pom.xml clean install -DskipTests
RUN mvn -B -q -f user-service/pom.xml clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:17-jre-alpine
RUN addgroup -S app && adduser -S app -G app
USER app
WORKDIR /app

COPY --from=build /build/user-service/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-jar","app.jar"]