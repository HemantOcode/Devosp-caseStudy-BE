# ---------- Build Stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

# Copy POMs (for caching)
COPY common/pom.xml common/pom.xml
COPY api-gateway/pom.xml api-gateway/pom.xml

# Copy common source and build it
COPY common/src common/src
RUN mvn -B -f common/pom.xml clean install -DskipTests

# Pre-download gateway deps
RUN mvn -B -f api-gateway/pom.xml dependency:go-offline

# Copy gateway source & build
COPY api-gateway/src api-gateway/src
RUN mvn -B -f api-gateway/pom.xml clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S app && adduser -S app -G app
USER app
WORKDIR /app

COPY --from=build /build/api-gateway/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
