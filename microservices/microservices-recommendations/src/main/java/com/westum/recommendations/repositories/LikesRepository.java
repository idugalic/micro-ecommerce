package com.westum.recommendations.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.westum.recommendations.model.Likes;

@RepositoryRestResource(exported=false)
public interface LikesRepository extends GraphRepository<Likes> {
}
