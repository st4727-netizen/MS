# =========================
# STAGE 1: BUILD (Maven + JDK)
# =========================
FROM maven:3.9.11-eclipse-temurin-17-alpine  AS build
  
WORKDIR /app

# Opcional: copiar solo pom.xml primero para cachear dependencias
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Ahora copiamos el resto del código y compilamos
COPY . .
RUN mvn -B clean package -P prd -DskipTests

# =========================
# STAGE 2: RUNTIME (solo JRE/JDK ligero)
# =========================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia del JAR desde el stage de build
COPY --from=build /app/target/*.jar app.jar

# Opcional: si usas un usuario sin privilegios
# RUN addgroup -S appgroup && adduser -S appuser -G appgroup
# USER appuser

EXPOSE 8083

  ENTRYPOINT ["java","-Dspring.profiles.active=prd","-jar","/app/app.jar"]
