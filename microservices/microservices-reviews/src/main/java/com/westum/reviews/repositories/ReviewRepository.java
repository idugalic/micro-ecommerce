package com.westum.reviews.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.westum.reviews.models.Review;

public interface ReviewRepository extends PagingAndSortingRepository<Review, String> {

    Iterable<Review> findByMlId(@Param("mlId") String mlId);
}
