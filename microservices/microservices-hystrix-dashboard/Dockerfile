FROM java:8
MAINTAINER idugalic@gmail.com
EXPOSE 7979
ENV APPLICATION_DOMAIN 192.168.59.103
ADD target/*.jar /hystrix.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/hystrix.jar"]