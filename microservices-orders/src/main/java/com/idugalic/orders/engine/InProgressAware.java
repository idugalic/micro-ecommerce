package com.idugalic.orders.engine;

import java.util.Set;

import com.idugalic.orders.order.domain.Order;

/**
 * Exposes the work currently in progress.
 * 
 */
public interface InProgressAware {

	/**
	 * Returns all {@link Order}s that currently prepared.
	 * 
	 * @return the {@link Order}s currently in preparation.
	 */
	Set<Order> getOrders();
}
