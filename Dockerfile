FROM maven:3.8.3-openjdk-11 AS build
COPY src /home/app/src
COPY lib /home/app/lib
COPY .mvn /home/app/.mvn
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM amazoncorretto:11
COPY --from=build /home/app/target/ROOT.jar /usr/local/lib/ROOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/ROOT.jar"]
