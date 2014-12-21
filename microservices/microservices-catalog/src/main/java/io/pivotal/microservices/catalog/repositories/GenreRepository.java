package io.pivotal.microservices.catalog.repositories;

import io.pivotal.microservices.catalog.models.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre findByMlId(String mlId);
}
