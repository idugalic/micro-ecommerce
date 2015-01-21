FROM java:8
MAINTAINER idugalic@gmail.com
EXPOSE 8081
ADD target/*.jar /reviews.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/reviews.jar"]