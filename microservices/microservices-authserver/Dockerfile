FROM java:8
MAINTAINER idugalic@gmail.com
EXPOSE 9999
ENV APPLICATION_DOMAIN 192.168.59.103
ADD target/*.jar /authserver.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/authserver.jar"]