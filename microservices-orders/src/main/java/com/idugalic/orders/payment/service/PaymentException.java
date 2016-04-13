package com.idugalic.orders.payment.service;


import org.springframework.util.Assert;

import com.idugalic.orders.order.domain.Order;

/**
 */

public class PaymentException extends RuntimeException {

	private static final long serialVersionUID = -4929826142920520541L;
	private final Order order;

	public PaymentException(Order order, String message) {

		super(message);

		Assert.notNull(order);
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}
	
}
