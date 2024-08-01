#define base docker image
FROM openjdk:17
ADD build/libs/movie-streaming-service-0.0.1-SNAPSHOT.jar  movie-streaming-service-docker.jar
ENTRYPOINT ["java","-jar", "movie-streaming-service-docker.jar"]
