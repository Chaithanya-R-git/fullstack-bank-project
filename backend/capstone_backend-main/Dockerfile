# 1️⃣ Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2️⃣ Runtime stage
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java","-Xms128m","-Xmx256m","-jar","app.jar"]
#ENTRYPOINT ["java","-Xms256m","-Xmx512m","-jar","app.jar"]
