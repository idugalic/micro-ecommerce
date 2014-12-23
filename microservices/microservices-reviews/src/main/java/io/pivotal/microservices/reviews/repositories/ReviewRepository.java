package io.pivotal.microservices.reviews.repositories;

import io.pivotal.microservices.reviews.models.Review;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends PagingAndSortingRepository<Review, String> {

    Iterable<Review> findByMlId(@Param("mlId") String mlId);
}
