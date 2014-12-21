package io.pivotal.microservices.recommendations;

import io.pivotal.microservices.recommendations.repositories.LikesRepository;
import io.pivotal.microservices.recommendations.repositories.MovieRepository;
import io.pivotal.microservices.recommendations.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "io.pivotal.microservices.recommendations.repositories")
@EnableEurekaClient
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    LikesRepository likesRepository;

    @Override
    public void run(String... strings) throws Exception {
        movieRepository.deleteAll();
        personRepository.deleteAll();
        likesRepository.deleteAll();
    }


}
