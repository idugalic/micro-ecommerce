package io.pivotal.microservices.reviews.repositories;

import io.pivotal.microservices.reviews.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {

    Iterable<Review> findByMlId(String mlId);
}
