package com.westum.orders.payment.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.util.Assert;

import com.westum.orders.order.domain.Order;

/**
 * A {@link Payment} done through a {@link CreditCard}.
 * 
 */
@Entity
public class CreditCardPayment extends Payment {

	@ManyToOne
	private CreditCard creditCard;
	

	public CreditCardPayment() {
		super();
	}


	/**
	 * Creates a new {@link CreditCardPayment} for the given {@link CreditCard} and {@link Order}.
	 * 
	 * @param creditCard must not be {@literal null}.
	 * @param order
	 */
	public CreditCardPayment(CreditCard creditCard, Order order) {

		super(order);
		Assert.notNull(creditCard);
		this.creditCard = creditCard;
	}


	public CreditCard getCreditCard() {
		return creditCard;
	}
	
	
}
