package io.pivotal.microservices.recommendations.repositories;

import io.pivotal.microservices.recommendations.model.Movie;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends GraphRepository<Movie> {
	@Query("MATCH (movie:Movie) WHERE movie.mlId = {mlId} RETURN movie")
	Movie findByMlId(@Param("mlId") String mlId);

    @Query("MATCH (p:Person) WHERE p.userName = {userName} MATCH p-[:LIKES]->movie<-[:LIKES]-slm-[:LIKES]->recommendations " +
            "WHERE not(p = slm) and not (p--recommendations) return recommendations")
    Iterable<Movie> recommendedMoviesFor(@Param("userName") String userName);

    @Query("MATCH (movie:Movie) WHERE movie.mlId = {mlId} MATCH movie<-[:LIKES]-slm-[:LIKES]->recommendations " +
            "RETURN distinct recommendations")
    Iterable<Movie> moviesLikedByPeopleWhoLiked(@Param("mlId") String mlId);
}

