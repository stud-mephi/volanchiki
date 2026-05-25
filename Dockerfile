FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Удаляем application.properties из JAR, чтобы не переопределял переменные окружения
RUN jar xf app.jar BOOT-INF/classes/application.properties \
    && sed -i 's/spring\.datasource\.url=.*//g' BOOT-INF/classes/application.properties \
    && sed -i 's/spring\.datasource\.username=.*//g' BOOT-INF/classes/application.properties \
    && sed -i 's/spring\.datasource\.password=.*//g' BOOT-INF/classes/application.properties \
    && sed -i 's/spring\.datasource\.driver-class-name=.*//g' BOOT-INF/classes/application.properties \
    && jar uf app.jar BOOT-INF/classes/application.properties \
    && rm -rf BOOT-INF

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
