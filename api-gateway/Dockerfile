# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY api-gateway/pom.xml ./api-gateway/
RUN mvn -f api-gateway/pom.xml dependency:go-offline -B

COPY api-gateway/src ./api-gateway/src
RUN mvn -f api-gateway/pom.xml clean package -DskipTests

# Stage 2:Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/api-gateway/target/*.jar app.jar

# Listining on 8080

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]