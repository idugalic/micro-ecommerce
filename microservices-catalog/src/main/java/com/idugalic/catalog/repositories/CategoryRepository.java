package com.idugalic.catalog.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.idugalic.catalog.models.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
}
