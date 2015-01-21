FROM java:8
MAINTAINER idugalic@gmail.com
ENV APPLICATION_DOMAIN 192.168.59.103
EXPOSE 8761
ADD target/*.jar /eureka.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker", "-jar","/eureka.jar"]