# Etapa 1: Compilación con Maven Wrapper (Java 26)
FROM eclipse-temurin:26-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Etapa 2: Ejecución (Java 26)
FROM eclipse-temurin:26-jre-alpine
WORKDIR /app
COPY --from=build /app/target/grocery-store-management-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
