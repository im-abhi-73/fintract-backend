FROM eclipse-temurin:21-jre
WORKDIR /app

COPY target/FinTrackSystem-0.0.1-SNAPSHOT.jar fintrack-v1.0.jar
EXPOSE 9000

ENTRYPOINT ["java","-jar","fintrack-v1.0.jar"]