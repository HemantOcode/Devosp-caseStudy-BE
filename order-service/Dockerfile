# --- Build Stage ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copy ALL pom files first to prepare for dependency resolution
COPY pom.xml .
COPY common/pom.xml common/
COPY order-service/pom.xml order-service/

# 2. Build and Install the 'common' module first
# This puts common-1.0.0.jar into the local /root/.m2/repository
COPY common/src common/src
RUN mvn -f common/pom.xml clean install -DskipTests -B

# 3. Now build the order-service
# It will now find 'common' in the local repo instead of looking on the internet
COPY order-service/src order-service/src
RUN mvn -f order-service/pom.xml clean package -DskipTests -B

# --- Runtime Stage ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/order-service/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]