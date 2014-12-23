package io.pivotal.microservices.catalog.repositories;

import io.pivotal.microservices.catalog.models.Genre;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {
    Genre findByMlId(@Param("mlId") String mlId);
}
