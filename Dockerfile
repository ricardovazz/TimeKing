FROM maven:3-adoptopenjdk-11 as BUILDER
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src/

RUN mvn clean package
COPY target/backend-${VERSION}.jar target/application.jar

FROM adoptopenjdk/openjdk11
WORKDIR /app/

COPY --from=BUILDER /build/target/application.jar /app/
CMD java -jar /app/application.jar

#mvn clean
#docker build -t backend .
#docker run -p 8080:8080 -d backend
#docker ps
#docker stop ID