FROM java:8
MAINTAINER idugalic@gmail.com
EXPOSE 7979
ADD target/*.jar /hystrix.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/hystrix.jar"]