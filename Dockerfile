#
# Build stage
#
FROM maven:3.5-jdk-8 AS build
COPY . /usr/src/app/
WORKDIR /usr/src/app/
RUN mvn -B -T 4C clean install -DskipTests
#
# Package stage
#
FROM gcr.io/distroless/java
COPY --from=build /usr/src/app/mainApp/target/shopping*.jar /usr/app/challenge.jar
ENTRYPOINT ["java","-jar","/usr/app/challenge.jar"]