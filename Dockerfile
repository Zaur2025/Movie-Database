# Используем официальный образ с Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Копируем только pom.xml сначала (для кэширования)
COPY pom.xml .

# Скачиваем зависимости
RUN mvn dependency:go-offline -B

# Копируем исходный код
COPY src ./src

# Сборка с использованием СИСТЕМНОГО Maven
RUN mvn clean package -DskipTests

# Финальный образ
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Безопасность
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]