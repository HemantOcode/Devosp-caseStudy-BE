# --- Build Stage ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copy the ROOT pom first
COPY pom.xml .
COPY ${{ matrix.service }}/ /app/${{ matrix.service }}/

# 2. Copy and install common
COPY common/pom.xml common/
COPY common/src common/src
RUN mvn -f common/pom.xml clean install -DskipTests -B

# 3. Copy and build the service
COPY order-service/pom.xml order-service/
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