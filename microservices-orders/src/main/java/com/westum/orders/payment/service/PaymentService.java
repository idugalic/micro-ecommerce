package com.westum.orders.payment.service;

import com.westum.orders.order.domain.Order;
import com.westum.orders.payment.domain.CreditCard;
import com.westum.orders.payment.domain.CreditCardNumber;
import com.westum.orders.payment.domain.CreditCardPayment;
import com.westum.orders.payment.domain.Payment;
import com.westum.orders.payment.domain.Payment.Receipt;

/**
 * Interface to collect payment services.
 * 
 */
public interface PaymentService {

	/**
	 * Pay the given {@link Order} with the {@link CreditCard} identified by the given {@link CreditCardNumber}.
	 * 
	 * @param order
	 * @param creditCardNumber
	 * @return
	 */
	CreditCardPayment pay(Order order, CreditCardNumber creditCardNumber);

	/**
	 * Returns the {@link Payment} for the given {@link Order}.
	 * 
	 * @param order
	 * @return the {@link Payment} for the given {@link Order} or {@literal null} if the Order hasn't been payed yet.
	 */
	Payment getPaymentFor(Order order);

	/**
	 * Takes the receipt
	 * 
	 * @param order
	 * @return
	 */
	Receipt takeReceiptFor(Order order);
}
