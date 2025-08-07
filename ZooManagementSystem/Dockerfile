FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

COPY . /app

RUN mvn clean install -DskipTests -Dmaven.test.skip=true

EXPOSE 8080

CMD ["java", "-jar", "target/*.jar"]
