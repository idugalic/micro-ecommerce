package io.pivotal.microservices.recommendations.repositories;

import io.pivotal.microservices.recommendations.model.Likes;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "likes", path = "likes")
public interface LikesRepository extends GraphRepository<Likes> {
}
