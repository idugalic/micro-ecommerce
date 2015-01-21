FROM java:8
MAINTAINER idugalic@gmail.com
EXPOSE 8080
ADD target/*.jar /catalog.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/catalog.jar"]