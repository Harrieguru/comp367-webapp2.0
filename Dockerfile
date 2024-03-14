# Use an existing Maven image with a shell (e.g., based on Debian)
FROM maven:3.8.4 AS builder

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the Maven project file and download dependencies
COPY pom.xml .
RUN mvn -B dependency:resolve-plugins dependency:resolve

# Copy the project source code
COPY src/ src/

# Build the application
RUN mvn -B clean package

# Create the final image
FROM openjdk:17-jdk AS final

# Set the working directory in the container
WORKDIR /app

# Copy the compiled JAR file from the builder stage to the final image
COPY --from=builder /usr/src/app/target/comp367-webapp-0.0.1-SNAPSHOT.jar ./comp367-webapp-0.0.1-SNAPSHOT.jar

# Define the command to run the application
CMD ["java", "-jar", "comp367-webapp-0.0.1-SNAPSHOT.jar"]
