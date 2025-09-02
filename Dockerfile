# ===============================
# Stage 1: Build with Maven
# ===============================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies (for better caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# ===============================
# Stage 2: Runtime (slim JRE)
# ===============================
FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app

# Copy the fat JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Create uploads directory and set permissions
RUN mkdir -p /app/uploads/profile_img /app/uploads/product_img /app/uploads/category_img && \
    chmod -R 755 /app/uploads

# Expose port
EXPOSE 8080

# Run the app with externalized config
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/config/application.properties"]
