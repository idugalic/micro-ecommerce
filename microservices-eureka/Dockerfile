FROM java:8
MAINTAINER idugalic@gmail.com
EXPOSE 8761
ADD target/*.jar /app.jar
RUN bash -c 'touch /app.jar'
CMD ["java","-Dspring.profiles.active=docker","-jar","/app.jar"]