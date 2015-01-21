FROM java:8
MAINTAINER idugalic@gmail.com
EXPOSE 9999
ADD target/*.jar /authserver.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/authserver.jar"]