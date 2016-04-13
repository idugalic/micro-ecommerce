package com.idugalic.orders.order.web;

import org.joda.time.DateTime;
import org.springframework.data.rest.core.config.Projection;

import com.idugalic.orders.order.domain.Order;
import com.idugalic.orders.order.domain.Order.Status;

/**
 * Projection interface to render {@link Order} summaries.
 * 
 */
@Projection(name = "summary", types = Order.class)
public interface OrderProjection {

	/**
	 * @see Order#getOrderedDate()
	 * @return
	 */
	DateTime getOrderedDate();

	/**
	 * @see Order#getStatus()
	 * @return
	 */
	Status getStatus();
}
