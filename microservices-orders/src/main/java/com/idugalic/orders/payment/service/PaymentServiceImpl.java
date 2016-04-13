package com.idugalic.orders.payment.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idugalic.orders.order.domain.Order;
import com.idugalic.orders.order.domain.Order.Status;
import com.idugalic.orders.order.repository.OrderRepository;
import com.idugalic.orders.payment.domain.CreditCard;
import com.idugalic.orders.payment.domain.CreditCardNumber;
import com.idugalic.orders.payment.domain.CreditCardPayment;
import com.idugalic.orders.payment.domain.Payment;
import com.idugalic.orders.payment.domain.Payment.Receipt;
import com.idugalic.orders.payment.repository.CreditCardRepository;
import com.idugalic.orders.payment.repository.PaymentRepository;


/**
 * Implementation of {@link PaymentService} delegating persistence operations to {@link PaymentRepository} and
 * {@link CreditCardRepository}.
 * 
 */
@Service
@Transactional
class PaymentServiceImpl implements PaymentService {
	@Autowired
	private CreditCardRepository creditCardRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderRepository orderRepository;

	public PaymentServiceImpl() {
		super();
	}

	public PaymentServiceImpl(CreditCardRepository creditCardRepository,
			PaymentRepository paymentRepository,
			OrderRepository orderRepository) {
		super();
		this.creditCardRepository = creditCardRepository;
		this.paymentRepository = paymentRepository;
		this.orderRepository = orderRepository;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springsource.restbucks.payment.PaymentService#pay(org.springsource.restbucks.order.Order, org.springsource.restbucks.payment.Payment)
	 */
	@Override
	public CreditCardPayment pay(Order order, CreditCardNumber creditCardNumber) {

		if (order.isPaid()) {
			throw new PaymentException(order, "Order already paid!");
		}

		CreditCard creditCard = creditCardRepository.findByNumber(creditCardNumber);

		if (creditCard == null) {
			throw new PaymentException(order, String.format("No credit card found for number: %s",
					creditCardNumber.getNumber()));
		}

		if (!creditCard.isValid()) {
			throw new PaymentException(order, String.format("Invalid credit card with number %s, expired %s!",
					creditCardNumber.getNumber(), creditCard.getExpirationDate()));
		}

		order.markPaid();
		return paymentRepository.save(new CreditCardPayment(creditCard, order));
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springsource.restbucks.payment.PaymentService#getPaymentFor(org.springsource.restbucks.order.Order)
	 */
	@Override
	@Transactional(readOnly = true)
	public Payment getPaymentFor(Order order) {
		return paymentRepository.findByOrder(order);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springsource.restbucks.payment.PaymentService#takeReceiptFor(org.springsource.restbucks.order.Order)
	 */
	@Override
	public Receipt takeReceiptFor(Order order) {

		order.setStatus(Status.TAKEN);
		orderRepository.save(order);

		Payment payment = getPaymentFor(order);
		return payment == null ? null : payment.getReceipt();
	}
}
