# ---------- Build Stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

# Copy POMs first (better caching)
COPY common/pom.xml common/pom.xml
COPY order-service/pom.xml order-service/pom.xml

# Install common module first
COPY common/src common/src
RUN mvn -B -f common/pom.xml clean install -DskipTests

# Pre-fetch service dependencies (common now exists locally)
RUN mvn -B -f order-service/pom.xml dependency:go-offline

# Copy service source and build
COPY order-service/src order-service/src
RUN mvn -B -f order-service/pom.xml clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:17-jre-alpine

# Non-root user
RUN addgroup -S app && adduser -S app -G app
USER app

WORKDIR /app
COPY --from=build /build/order-service/target/*.jar app.jar

EXPOSE 8082

# POC-safe entrypoint (no JVM tuning)
ENTRYPOINT ["java","-jar","app.jar"]