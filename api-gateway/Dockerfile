# ---------- Build Stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

COPY common/pom.xml common/pom.xml
COPY api-gateway/pom.xml api-gateway/pom.xml

RUN mvn -B -q -f api-gateway/pom.xml dependency:go-offline

COPY common/src common/src
COPY api-gateway/src api-gateway/src

RUN mvn -B -q -f common/pom.xml clean install -DskipTests
RUN mvn -B -q -f api-gateway/pom.xml clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:17-jre-alpine
RUN addgroup -S app && adduser -S app -G app
USER app
WORKDIR /app

COPY --from=build /build/api-gateway/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-jar","app.jar"]