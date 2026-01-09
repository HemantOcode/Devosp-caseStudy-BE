# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY common/pom.xml ./common/
COPY order-service/pom.xml ./order-service/

RUN mvn -f common/pom.xml dependency:go-offline -B
RUN mvn -f order-service/pom.xml dependency:go-offline -B

COPY common/src ./common/src
RUN mvn -f common/pom.xml clean install -DskipTests -B

COPY order-service/src ./order-service/src
RUN mvn -f order-service/pom.xml clean package -DskipTests -B

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/order-service/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]