#
# Build stage
#
FROM maven:latest AS build
COPY showingOffers/src /home/app/showingOffers/src
COPY showingOffers/pom.xml /home/app/showingOffers
COPY pom.xml /home/app
RUN mvn -f /home/app/showingOffers/pom.xml -Dmaven.test.skip=true clean package

#
# Package stage
#
FROM openjdk:17-alpine
COPY --from=build /home/app/showingOffers/target/ /usr/local/lib/
EXPOSE 8081
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod","/usr/local/lib/showingOffers-0.0.1-SNAPSHOT.jar"]