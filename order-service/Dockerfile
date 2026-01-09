# ---------- Build Stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

# Copy pom files first (cache-friendly)
COPY common/pom.xml common/pom.xml
COPY order-service/pom.xml order-service/pom.xml

# Pre-fetch dependencies
RUN mvn -B -q -f order-service/pom.xml dependency:go-offline

# Copy source code
COPY common/src common/src
COPY order-service/src order-service/src

# Build common
RUN mvn -B -q -f common/pom.xml clean install -DskipTests

# Build service
RUN mvn -B -q -f order-service/pom.xml clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:17-jre-alpine

# Security hardening
RUN addgroup -S app && adduser -S app -G app
USER app

WORKDIR /app

COPY --from=build /build/order-service/target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-jar","app.jar"]