package io.pivotal.microservices.recommendations;

import io.pivotal.microservices.recommendations.model.Likes;
import io.pivotal.microservices.recommendations.model.Movie;
import io.pivotal.microservices.recommendations.model.Person;
import io.pivotal.microservices.recommendations.repositories.LikesRepository;
import io.pivotal.microservices.recommendations.repositories.MovieRepository;
import io.pivotal.microservices.recommendations.repositories.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "io.pivotal.microservices.recommendations.repositories")
@EnableDiscoveryClient
public class Application extends RepositoryRestMvcConfiguration implements CommandLineRunner {
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
    @Override
	protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Movie.class, Likes.class, Person.class);
	}

}
