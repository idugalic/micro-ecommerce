package com.westum.recommendations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.westum.recommendations.model.Likes;
import com.westum.recommendations.model.Movie;
import com.westum.recommendations.model.Person;
import com.westum.recommendations.repositories.LikesRepository;
import com.westum.recommendations.repositories.MovieRepository;
import com.westum.recommendations.repositories.PersonRepository;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "com.westum.recommendations.repositories")
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
