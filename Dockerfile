# 1. Fase de construcción
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Corregimos el "Recursively copying": Copiamos solo lo necesario
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# 2. Fase de ejecución
FROM eclipse-temurin:17-jdk-jammy

# Corregimos el "Privileged user": Creamos un usuario de sistema
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Copiamos el jar desde la fase anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

# Ejecutamos la app con el usuario no privilegiado
ENTRYPOINT ["java", "-jar", "/app.jar"]