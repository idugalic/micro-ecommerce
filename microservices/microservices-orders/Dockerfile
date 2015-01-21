FROM java:8
MAINTAINER idugalic@gmail.com
EXPOSE 8083
ADD target/*.jar /orders.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/orders.jar"]