package io.pivotal.microservices.catalog.repositories;

import io.pivotal.microservices.catalog.models.Movie;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    public Movie findByMlId(@Param("mlId") String mlId);
}
