# Use an official OpenJDK runtime as a parent image
FROM maven:3.9.8-eclipse-temurin-21 as build
WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests


# Set the working directory in the container
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the jar file into the container
COPY --from=build /app/target/*.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]