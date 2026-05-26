# Etapa 1: build da aplicacao Java
FROM maven:3.9.9-eclipse-temurin-17 AS backend-build
WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: imagem final do backend
FROM eclipse-temurin:17-jre AS backend
WORKDIR /app

COPY --from=backend-build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Etapa 3: imagem final do frontend
FROM nginx:1.27-alpine AS frontend

COPY frontend-tech/nginx.conf /etc/nginx/conf.d/default.conf
COPY frontend-tech/index.html /usr/share/nginx/html/index.html
COPY frontend-tech/styles.css /usr/share/nginx/html/styles.css
COPY frontend-tech/app.js /usr/share/nginx/html/app.js

EXPOSE 80
