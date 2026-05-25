FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Создаём пустой application.properties, чтобы Spring Boot брал всё из переменных окружения
RUN echo "server.port=\${PORT:8080}" > /app/application.properties

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/application.properties"]
