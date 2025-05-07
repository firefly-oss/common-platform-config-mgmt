FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY common-platform-config-mgmt.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]