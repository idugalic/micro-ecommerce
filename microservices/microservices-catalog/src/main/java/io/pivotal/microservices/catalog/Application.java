package io.pivotal.microservices.catalog;

import io.pivotal.microservices.catalog.models.Genre;
import io.pivotal.microservices.catalog.models.Movie;
import io.pivotal.microservices.catalog.repositories.GenreRepository;
import io.pivotal.microservices.catalog.repositories.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaRepositories
@EnableDiscoveryClient
@RestController
public class Application extends RepositoryRestMvcConfiguration {

	@Override
	protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Movie.class, Genre.class);
	}
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    GenreRepository genreRepository;

    @RequestMapping(value = "/catalog/movies", method = RequestMethod.GET)
    public Iterable<Movie> movies() {
        return movieRepository.findAll();
    }

    @RequestMapping(value = "/catalog/movies/{mlId}", method = RequestMethod.GET)
    public Movie movie(@PathVariable String mlId) {
        return movieRepository.findByMlId(mlId);
    }

    @RequestMapping(value = "/catalog/genres", method = RequestMethod.GET)
    public Iterable<Genre> genres() {
        return genreRepository.findAll();
    }

    @RequestMapping(value = "/catalog/genres/{mlId}", method = RequestMethod.GET)
    public Genre genre(@PathVariable String mlId) {
        return genreRepository.findByMlId(mlId);
    }
}
