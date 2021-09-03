FROM openjdk:11
VOLUME /tmp
EXPOSE 8082
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]