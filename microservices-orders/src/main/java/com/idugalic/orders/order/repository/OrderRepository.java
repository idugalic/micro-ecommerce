package com.idugalic.orders.order.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.idugalic.orders.order.domain.Order;
import com.idugalic.orders.order.domain.Order.Status;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

	List<Order> findByStatus(@Param("status") Status status);
}
