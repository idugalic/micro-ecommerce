package com.idugalic.orders.payment.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.idugalic.orders.order.domain.Order;
import com.idugalic.orders.payment.domain.Payment;
import com.idugalic.orders.payment.domain.Payment.Receipt;

/**
 * Helper component to create links to the {@link Payment} and {@link Receipt}.
 * 
 *
 */
@Component
class PaymentLinks {

	static final String PAYMENT = "/payment";
	static final String RECEIPT = "/receipt";
	static final String PAYMENT_REL = "payment";
	static final String RECEIPT_REL = "receipt";

	private EntityLinks entityLinks;

	@Autowired
	public PaymentLinks(EntityLinks entityLinks) {
		this.entityLinks = entityLinks;
	}

	/**
	 * Returns the {@link Link} to point to the {@link Payment} for the given
	 * {@link Order}.
	 * 
	 * @param order
	 *            must not be {@literal null}.
	 * @return
	 */
	Link getPaymentLink(Order order) {
		return entityLinks.linkForSingleResource(order).slash(PAYMENT).withRel(PAYMENT_REL);
	}

	/**
	 * Returns the {@link Link} to the {@link Receipt} of the given
	 * {@link Order}.
	 * 
	 * @param order
	 * @return
	 */
	Link getReceiptLink(Order order) {
		return entityLinks.linkForSingleResource(order).slash(RECEIPT).withRel(RECEIPT_REL);
	}
}
