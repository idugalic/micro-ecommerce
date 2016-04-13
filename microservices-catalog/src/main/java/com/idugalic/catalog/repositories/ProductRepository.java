package com.idugalic.catalog.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.idugalic.catalog.models.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
