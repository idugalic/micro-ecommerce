package io.pivotal.microservices.recommendations.controllers;

import io.pivotal.microservices.recommendations.model.Likes;
import io.pivotal.microservices.recommendations.model.Movie;
import io.pivotal.microservices.recommendations.model.Person;
import io.pivotal.microservices.recommendations.repositories.LikesRepository;
import io.pivotal.microservices.recommendations.repositories.MovieRepository;
import io.pivotal.microservices.recommendations.repositories.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(Likes.class)
public class ApiController {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    LikesRepository likesRepository;

    //This controller is constructed for this method only. Enables users to create likes much easier.
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

}
