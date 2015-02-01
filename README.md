# Ecommerce application

Rest-full, Hipermedia-based distributed  ecommerce application.

## REST based micro-services with Spring Boot

- Catalog(JPA-SQL product calatlog).
- Reviews (MongoDB product reviews)
- Recommendations (Neo4J recommendations)
- Orders (JPA)
- API Gateway (Reactive API proxy)

![gate2.png](https://bitbucket.org/repo/L66odr/images/3435389082-gate2.png)


### Catalog

The Catalog consists of categorized products. Products can be in one ore more categories, and category can contain one ore more products.
Products and Categories are exposed as REST resources using Spring Data RESTs capability to automatically expose Spring Data JPA repositories contained in the application.

### Reviews 

Review is entity(document) related to product by productId and to customer(user) by userName. The repository under this service is MongoDb
Reviews are exposed as REST resources using Spring Data RESTs capability to automatically expose Spring Data Mongo repositories contained in the application.

### Recommendations 

This service consists of Person and Product entities and Like relation entity that links them.
Recommendations are exposed as REST resources using Spring Data RESTs capability to automatically expose Spring Data Neo4j(Graph) repositories contained in the application.

### Orders

The implementation consists of mainly two parts, the order and the payment part. The Orders are exposed as REST resources using Spring Data RESTs capability to automatically expose Spring Data JPA repositories contained in the application. The Payment process  are implemented manually using a Spring MVC controller (PaymentController).

We're using Spring Data REST to expose the OrderRepository as REST resource without additional effort.

Spring Hateoas provides a generic Resource abstraction that we leverage to create hypermedia-driven representations. Spring Data REST also leverages this abstraction so that we can deploy ResourceProcessor implementations (e.g. PaymentorderResourceProcessor) to enrich the representations for Order instance with links to the PaymentController.

![order_state.png](https://bitbucket.org/repo/L66odr/images/1347317380-order_state.png)
![orders.png](https://bitbucket.org/repo/L66odr/images/691856836-orders.png)

###  API Gateway

Implementation of an API gateway that is the single entry point for all clients. The API gateway handles requests in one of two ways. Some requests are simply proxied/routed to the appropriate service. It handles other requests by fanning out to multiple services.

http://microservices.io/patterns/apigateway.html

## Configuration and management services:

- Hystrix is used to monitor the availability of the remote system, so if it fails to connect 20 times in 5 seconds (by default) the circuit will open and no more attempts will be made until after a timeout.
- Eureka is used as service registry and discovery.
- Config server is used for centralized configuration.
- Authorization (Oauth2) server for issuing tokens.


## Running Instructions
### ___Local____
- Before try to run the services, make sure you have Neo4J and MongoDB running on localhost (on default ports).
- To run the services localy, just execute "mvn spring-boot:run" in each project subfolder. Run Authorization server project first.
- After you run services, trigger shell scripts under script folder of each service to create sample data.

#### Usage

- Get a token: $ curl -X POST -vu acme:acmesecret http://localhost:9999/uaa/oauth/token -H "Accept: application/json" -d "password=idugalic&username=idugalic&grant_type=password&scope=openid&client_secret=acmesecret&client_id=acme"
- Catalog service: $ curl http://localhost:8080/ -H "Authorization: Bearer <YOUR TOKEN>"
- Reviews service: $ curl http://localhost:8081/ -H "Authorization: Bearer <YOUR TOKEN>"
- Recommendations service: $ curl http://localhost:8082/ -H "Authorization: Bearer <YOUR TOKEN>"
- Orders service: $ curl http://localhost:8083/ -H "Authorization: Bearer <YOUR TOKEN>"
- Catalog service(proxy) : $ curl http://localhost:9000/catalog -H "Authorization: Bearer <YOUR TOKEN>"
- Reviews service(proxy): $ curl http://localhost:9000/reviews -H "Authorization: Bearer <YOUR TOKEN>"
- Recommendations service(proxy): $ curl http://localhost:9000/recommendations -H "Authorization: Bearer <YOUR TOKEN>"
- Orders service(proxy): $ curl http://localhost:9000/orders -H "Authorization: Bearer <YOUR TOKEN>"
- Mobile service (agregate): $ curl http://localhost:9000/product/1 -H "Authorization: Bearer <YOUR TOKEN>"

#### HAL Browser.
HAL browser is an API browser for the hal+json media type. 

If you don't like command line CURL feel free to use HAL browser.
It is available on the http://localhost:9000/browser/index.html address.

Example of usage:


- Get a token: $ curl -X POST -vu acme:acmesecret http://localhost:9999/uaa/oauth/token -H "Accept: application/json" -d "password=idugalic&username=idugalic&grant_type=password&scope=openid&client_secret=acmesecret&client_id=acme"
- Paste your token in 'Custom Request Header' like this: Authorization: Bearer <YOUR TOKEN>
- Explore the API :)

### ___Docker___
- Before try to run the services, make sure you have Docker and Fig (optional) installed on your machine.
- You can run all your services and databases in Docker via Fig. 'fig.yml' is provided in root folder. 
- You can run/install all your services and databases with one command '$ fig run' from root folder.
- Fig is not mandatory (I had some problems with 32bit version of fig on bot2docker for windows). You can run fig as Docker container '$ docker run -v $(pwd):/app -v /var/run/docker.sock:/var/run/docker.sock -ti dduportal/fig up -d'
- NOTE: I am runing boot2docker for Windows. Docker is runing in virtual machine with IP of 192.168.59.103. This is externalized in Dockerfile-s!