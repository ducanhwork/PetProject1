# Stage 1 - Build
#Start with a maven image that includes jdk 17
FROM maven:3.9.8-amazoncorretto-17 AS build

#Copy the source code and pom.xml to the container
WORKDIR /app
COPY pom.xml .
COPY src ./src

#Build the application
RUN mvn package -DskipTests

# Stage 2 - create image
#Start with a jdk 17 image
FROM amazoncorretto:17.0.8

#Set the working directory to /app and copy the jar file from the build stage
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

#Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]