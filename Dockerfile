# 1. Use Maven with JDK 17 to build the app
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline

# Copy source code and build
COPY src src
RUN ./mvnw package -DskipTests

# 2. Use a smaller JDK image just to run the app
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render will map it dynamically)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]