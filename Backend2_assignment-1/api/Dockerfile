#FROM openjdk:18.0.1-jdk-oraclelinux8
#
#EXPOSE 8080
#WORKDIR /ourapp
#COPY . ./
#
#RUN ./mvnw package
##-Dmaven.test.skip
#
#ENTRYPOINT ["java", "-jar", "./target/webshop-docker.jar"]

#FROM maven:3-openjdk-18-slim AS build
#WORKDIR /home/app
#COPY src ./src
#COPY pom.xml .
#RUN mvn clean package
#
##
## Package stage
##
#FROM eclipse-temurin
#WORKDIR /home/app
#COPY --from=build /home/app/target/webshop-*.jar /usr/local/lib/api.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/usr/local/lib/api.jar"]

FROM maven:3-openjdk-18-slim AS build

# fetch maven dependencies
WORKDIR /home/app
COPY pom.xml pom.xml
RUN mvn dependency:go-offline

# build
COPY src src
RUN mvn clean package

# get result from build stage
#FROM openjdk:19-ea-22-slim-buster
FROM eclipse-temurin:18.0.1_10-jre
WORKDIR /home/app
COPY --from=build /home/app/target/webshop-*.jar /usr/local/lib/api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/api.jar"]