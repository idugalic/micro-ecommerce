package com.westum.catalog.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.westum.catalog.models.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
