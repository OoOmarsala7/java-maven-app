FROM openjdk:8-jre-alpine
RUN mkdir /usr/app
COPY ./target/java-maven-app-*.jar /usr/app
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "java-maven-app-*" ]