FROM java:8
MAINTAINER idugalic@gmail.com
EXPOSE 8082
ADD target/*.jar /recommendations.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/recommendations.jar"]