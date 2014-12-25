package com.westum.recommendations.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.westum.recommendations.model.Product;

public interface ProductRepository extends GraphRepository<Product> {
	@Query("MATCH (product:Product) WHERE product.productId = {productId} RETURN product")
	Product findByProductId(@Param("productId") String productId);

    @Query("MATCH (p:Person) WHERE p.userName = {userName} MATCH p-[:LIKES]->movie<-[:LIKES]-slm-[:LIKES]->recommendations " +
            "WHERE not(p = slm) and not (p--recommendations) return recommendations")
    Iterable<Product> recommendedProductsFor(@Param("userName") String userName);

    @Query("MATCH (product:Product) WHERE product.productId = {productId} MATCH movie<-[:LIKES]-slm-[:LIKES]->recommendations " +
            "RETURN distinct recommendations")
    Iterable<Product> productsLikedByPeopleWhoLiked(@Param("productId") String productId);
}

