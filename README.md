# Ecommerce application

Simple ecommerce application 

## REST based micro-services 

 Spring Boot based Maven projects that are standalone applications (microservices):

- Catalog(JPA-SQL product calatlog).
- Reviews (MongoDB product reviews)
- Recommendations (Neo4J recommendations)
- Orders (JPA)
- API Gateway (Reactive API proxy)

Configuration and management services:

- Hystrix is used to monitor the availability of the remote system, so if it fails to connect 20 times in 5 seconds (by default) the circuit will open and no more attempts will be made until after a timeout.
- Eureka is used as service registry and discovery.
- Config server is used for centralized configuration.
- Authorization (Oauth2) server for issuing tokens.


## Running Instructions
### Local
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

### Docker images
todo