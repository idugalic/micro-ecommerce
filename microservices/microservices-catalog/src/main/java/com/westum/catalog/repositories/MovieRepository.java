package com.westum.catalog.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.westum.catalog.models.Movie;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    public Movie findByMlId(@Param("mlId") String mlId);
}
