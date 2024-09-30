FROM maven:3.9.0-amazoncorretto-17 AS build
COPY nachrichtenproxy/src /home/app/src
COPY nachrichtenproxy/pom.xml /home/app
WORKDIR /home/app
# invalidate cache
# RUN date
RUN --mount=type=cache,id=fotouploader-m2-cache,sharing=shared,target=/root/.m2 mvn package -DskipTests

FROM amazoncorretto:17-alpine-jdk
RUN apk add --no-cache ffmpeg
WORKDIR /app
COPY --from=build /home/app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]