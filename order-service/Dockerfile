FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy common module first
COPY common/pom.xml ./common/
COPY common/src ./common/src
RUN mvn -f common/pom.xml clean install -DskipTests

# Copy and build order service
COPY order-service/pom.xml ./order-service/
COPY order-service/src ./order-service/src
RUN mvn -f order-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/order-service/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
