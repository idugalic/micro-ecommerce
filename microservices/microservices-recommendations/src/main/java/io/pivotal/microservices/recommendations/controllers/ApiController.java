package io.pivotal.microservices.recommendations.controllers;

import io.pivotal.microservices.recommendations.model.Likes;
import io.pivotal.microservices.recommendations.model.Movie;
import io.pivotal.microservices.recommendations.model.Person;
import io.pivotal.microservices.recommendations.repositories.LikesRepository;
import io.pivotal.microservices.recommendations.repositories.MovieRepository;
import io.pivotal.microservices.recommendations.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    LikesRepository likesRepository;

    @RequestMapping(value = "/recommendations/movies", method = RequestMethod.GET)
    public Iterable<Movie> movies() {
        return movieRepository.findAll();
    }

    @RequestMapping(value = "/recommendations/movies", method = RequestMethod.POST)
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        movieRepository.save(movie);
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/recommendations/people", method = RequestMethod.GET)
    public Iterable<Person> people() {
        return personRepository.findAll();
    }

    @RequestMapping(value = "/recommendations/people", method = RequestMethod.POST)
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        personRepository.save(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/recommendations/likes", method = RequestMethod.GET)
    public Iterable<Likes> likes() {
        return likesRepository.findAll();
    }

    @RequestMapping(value = "/recommendations/{userName}/likes/{mlId}", method = RequestMethod.POST)
    public ResponseEntity<Likes> createPersonMovieLink(@PathVariable String userName,
                                                       @PathVariable String mlId) {
        Person person = personRepository.findByUserName(userName);
        Movie movie = movieRepository.findByMlId(mlId);

        Likes likes = new Likes();
        likes.setPerson(person);
        likes.setMovie(movie);
        likesRepository.save(likes);

        return new ResponseEntity<>(likes, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/recommendations/forUser/{userName}", method = RequestMethod.GET)
    public Iterable<Movie> recommendedMoviesForUser(@PathVariable String userName) {
        return movieRepository.recommendedMoviesFor(userName);
    }

    @RequestMapping(value = "/recommendations/forMovie/{mlId}", method = RequestMethod.GET)
    public Iterable<Movie> recommendedMoviesForMovie(@PathVariable String mlId) {
        return movieRepository.moviesLikedByPeopleWhoLiked(mlId);
    }
}
