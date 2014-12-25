package com.westum.orders.payment.aspect;


import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.westum.orders.order.domain.Order;
import com.westum.orders.payment.event.OrderPaidEvent;
import com.westum.orders.payment.service.PaymentService;

/**
 * Aspect to publish an {@link OrderPaidEvent} on successful execution of
 * {@link PaymentService#pay(Order, CreditCardNumber)} <em>after</em> the transaction has completed. Manually defines
 * the order of the aspect to be <em>before</em> the transaction aspect.
 * 
 */
@Aspect
@Service
class PaymentAspect implements ApplicationEventPublisherAware, Ordered {

	private ApplicationEventPublisher publisher;

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationEventPublisherAware#setApplicationEventPublisher(org.springframework.context.ApplicationEventPublisher)
	 */
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE - 10;
	}

	/**
	 * Publishes an {@link OrderPaidEvent} for the given {@link Order}.
	 * 
	 * @param order
	 */
	@AfterReturning(value = "execution(* com.westum.orders.payment.service.PaymentService.pay(com.westum.orders.order.domain.Order, ..)) && args(order, ..)")
	public void triggerPaymentEvent(Order order) {

		if (order == null) {
			return;
		}
 
		this.publisher.publishEvent(new OrderPaidEvent(order.getId(), this));
	}
}
