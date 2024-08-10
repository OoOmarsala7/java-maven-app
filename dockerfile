FROM openjdk:8-jre-alpine
COPY ./target/java-maven-app-1.1.0-SNAPSHOT /usr/app
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "java-maven-app-1.1.0-SNAPSHOT" ]