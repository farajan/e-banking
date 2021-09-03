FROM openjdk:11
VOLUME /tmp
EXPOSE 8762
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]