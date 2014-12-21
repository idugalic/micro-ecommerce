package io.pivotal.microservices.catalog.repositories;

import io.pivotal.microservices.catalog.models.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    public Movie findByMlId(String mlId);
}
