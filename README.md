# Micro Ecommerce application (Microservices architecture style - REST)

Refer to a doc folder for more information.

 Four Spring Boot based Maven projects that are standalone applications (microservices):

- Reviews (MongoDB)
- Recommendations (Neo4J)
- Catalog(JPA-SQL)
- API Gateway (Reactive API proxy)

Configuration and management services:

- Hystrix is used to monitor the availability of the remote system, so if it fails to connect 20 times in 5 seconds (by default) the circuit will open and no more attempts will be made until after a timeout.
- Eureka is used as service registry and discovery.
- Config server is used for centralized configuration.


## Running Instructions
- Before try to run the services, make sure you have Neo4J and MongoDB running on localhost.
- To run the services, just execute "mvn spring-boot:run" in each project subfolder.