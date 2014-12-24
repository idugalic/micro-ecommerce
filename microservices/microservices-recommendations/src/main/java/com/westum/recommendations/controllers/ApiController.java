package com.westum.recommendations.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.westum.recommendations.model.Likes;
import com.westum.recommendations.model.Movie;
import com.westum.recommendations.model.Person;
import com.westum.recommendations.repositories.LikesRepository;
import com.westum.recommendations.repositories.MovieRepository;
import com.westum.recommendations.repositories.PersonRepository;

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
