FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . /app
RUN ./mvnw -q -e -DskipTests package
EXPOSE 8080
CMD ["java", "-jar", "target/app.jar"]
