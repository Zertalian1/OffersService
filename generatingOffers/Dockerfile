#
# Build stage
#
FROM maven:latest AS build
COPY generatingOffers/src /home/app/generatingOffers/src
COPY generatingOffers/pom.xml /home/app/generatingOffers
COPY pom.xml /home/app
RUN mvn -f /home/app/generatingOffers/pom.xml -Dmaven.test.skip=true clean package

#
# Package stage
#
FROM openjdk:17-alpine
COPY --from=build /home/app/generatingOffers/target/ /usr/local/lib/
EXPOSE 8080
RUN apk --update add fontconfig ttf-dejavu
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod","/usr/local/lib/generatingOffers-0.0.1-SNAPSHOT.jar"]