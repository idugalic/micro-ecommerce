package com.idugalic.recommendations.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.idugalic.recommendations.model.Likes;

@RepositoryRestResource(exported=false)
public interface LikesRepository extends GraphRepository<Likes> {
}
