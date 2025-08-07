FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . /app


RUN chmod +x mvnw


RUN ls -la && ./mvnw -v

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/*.jar"]
