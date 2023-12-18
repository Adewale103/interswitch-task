FROM openjdk:17-alpine
MAINTAINER book-store
EXPOSE 6082
ARG JAR_FILE=target/book-store.jar
ADD ${JAR_FILE} book-store.jar
ENTRYPOINT ["java", "-jar", "/book-store.jar"]