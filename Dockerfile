# Use a lightweight OpenJDK image as the base
#FROM openjdk:17-jdk-slim as build

# Set the working directory inside the container
#WORKDIR /app

# Copy the pom.xml and the project source code
#COPY pom.xml .
#COPY src ./src

# Build the Spring Boot application using Maven
#RUN ./mvnw package -DskipTests
#-----------------------------------------

# Use a new stage for the final image to keep it small
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Expose the port the app runs on
EXPOSE 8080

# Copy the built JAR file from the 'build' stage
COPY target/bookshop-app-0.0.1-SNAPSHOT.jar bookshop-app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "bookshop-app.jar"]