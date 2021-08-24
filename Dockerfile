FROM maven:3.5.2-jdk-8-alpine
COPY pom.xml /usr/src/java-code/
COPY src /usr/src/java-code/src/
WORKDIR /usr/src/java-code
RUN mvn clean package

WORKDIR /usr/src/java-app
RUN cp /usr/src/java-code/target/dependency/jetty-runner.jar /usr/src/java-app/jetty-runner.jar
RUN cp /usr/src/java-code/target/SimpleJettyProject-0.1.war /usr/src/java-app/SimpleJettyProject.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "jetty-runner.jar", "SimpleJettyProject.war"]

