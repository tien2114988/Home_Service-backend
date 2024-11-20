# Use an official OpenJDK runtime as a parent image
FROM maven:3.9.8-eclipse-temurin-21 as build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY target/homeService-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]