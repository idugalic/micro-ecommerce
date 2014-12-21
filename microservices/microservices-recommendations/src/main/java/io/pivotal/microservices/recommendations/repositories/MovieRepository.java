package io.pivotal.microservices.recommendations.repositories;

import io.pivotal.microservices.recommendations.model.Movie;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface MovieRepository extends GraphRepository<Movie> {
    Movie findByMlId(String mlId);

    @Query("MATCH (p:Person) WHERE p.userName = {0} MATCH p-[:LIKES]->movie<-[:LIKES]-slm-[:LIKES]->recommendations " +
            "WHERE not(p = slm) and not (p--recommendations) return recommendations")
    Iterable<Movie> recommendedMoviesFor(String userName);

    @Query("MATCH (movie:Movie) WHERE movie.mlId = {0} MATCH movie<-[:LIKES]-slm-[:LIKES]->recommendations " +
            "RETURN distinct recommendations")
    Iterable<Movie> moviesLikedByPeopleWhoLiked(String mlId);
}

