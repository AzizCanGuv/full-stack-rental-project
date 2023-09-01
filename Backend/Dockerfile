FROM amazoncorretto:17 AS build

WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN chmod +x mvnw

RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -DskipTests

FROM amazoncorretto:17

WORKDIR /app

COPY --from=build /app/target/*.jar backend.jar

ENV SPRING_PROFILES_ACTIVE=docker


# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "backend.jar"]
