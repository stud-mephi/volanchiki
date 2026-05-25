# Шаг 1: Собираем приложение
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Шаг 2: Создаём лёгкий образ для запуска
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
