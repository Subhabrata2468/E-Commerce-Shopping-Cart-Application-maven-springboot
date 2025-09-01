# ===============================
# Stage 1: Build with Maven
# ===============================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies (better caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# ===============================
# Stage 2: Runtime with JDK
# ===============================
FROM eclipse-temurin:17-jdk AS runtime
WORKDIR /app

# Copy the fat JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Add updated application.properties (if you want to override inside image)
COPY application.properties /app/config/application.properties

# Expose port
EXPOSE 8080

# Run the app with custom config location
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/config/application.properties"]
