package com.idugalic.orders.payment.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.idugalic.orders.order.domain.Order;
import com.idugalic.orders.payment.domain.Payment;

/**
 * Repository interface to manage {@link Payment} instances.
 * 
 */
@RepositoryRestResource(exported=false)
public interface PaymentRepository extends PagingAndSortingRepository<Payment, Long> {

	/**
	 * Returns the payment registered for the given {@link Order}.
	 * 
	 * @param order
	 * @return
	 */
	Payment findByOrder(Order order);
}
