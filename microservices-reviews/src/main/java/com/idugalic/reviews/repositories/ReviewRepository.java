package com.idugalic.reviews.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.idugalic.reviews.models.Review;

public interface ReviewRepository extends PagingAndSortingRepository<Review, String> {

    Iterable<Review> findByProductId(@Param("productId") String productId);
}
