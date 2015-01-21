FROM java:8
MAINTAINER idugalic@gmail.com
EXPOSE 8761
ADD target/*.jar /eureka.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker", "-jar","/eureka.jar"]