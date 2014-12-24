package com.westum.catalog.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.westum.catalog.models.Genre;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {
    Genre findByMlId(@Param("mlId") String mlId);
}
