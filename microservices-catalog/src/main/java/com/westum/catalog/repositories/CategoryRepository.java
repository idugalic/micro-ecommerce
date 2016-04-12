package com.westum.catalog.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.westum.catalog.models.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
}
