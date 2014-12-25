package com.westum.orders.order.web;

import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception being thrown in case an {@link Order} has already been paid and a payment is re-attempted.
 * 
 */
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class OrderAlreadyPaidException extends RuntimeException {

	private static final long serialVersionUID = 1L;
}
