FROM java:8
MAINTAINER idugalic@gmail.com
EXPOSE 9000
ADD target/*.jar /api-gateway.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/api-gateway.jar"]