package com.idugalic.orders.order.web;

import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

import com.idugalic.orders.order.domain.Order;

/**
 * Event listener to reject {@code DELETE} requests to Spring Data REST.
 * 
 */
@Component
class OrderControllerEventListener extends AbstractRepositoryEventListener<Order> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.rest.repository.context.
	 * AbstractRepositoryEventListener#onBeforeDelete(java.lang.Object)
	 */
	@Override
	protected void onBeforeDelete(Order order) {

		if (order.isPaid()) {
			throw new OrderAlreadyPaidException();
		}
	}
}
