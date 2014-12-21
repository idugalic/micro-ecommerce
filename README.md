# Micro Ecommerce application (Microservices architecture style - REST)

Refer to a doc folder for more information.

There is nice spring guide also: https://spring.io/guides/tutorials/bookmarks/

##Overview

 Four Spring Boot based Maven projects that are standalone applications (microservices):

- Catalog(JPA-SQL movie calatlog).
- Reviews (MongoDB movie reviews)
- Recommendations (Neo4J recommendations)
- API Gateway (Reactive API proxy)

Configuration and management services:

- Hystrix is used to monitor the availability of the remote system, so if it fails to connect 20 times in 5 seconds (by default) the circuit will open and no more attempts will be made until after a timeout.
- Eureka is used as service registry and discovery.
- Config server is used for centralized configuration.


## Running Instructions
### Local
- Before try to run the services, make sure you have Neo4J and MongoDB running on localhost (on default ports).
- To run the services localy, just execute "mvn spring-boot:run" in each project subfolder.
- After you run services, trigger shell scripts under script folder of each service to create sample data.

### Docker images
todo