package io.pivotal.microservices.recommendations.repositories;

import io.pivotal.microservices.recommendations.model.Likes;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface LikesRepository extends GraphRepository<Likes> {
}
