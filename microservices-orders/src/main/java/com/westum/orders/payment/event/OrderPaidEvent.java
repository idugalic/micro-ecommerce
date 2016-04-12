package com.westum.orders.payment.event;


import org.springframework.context.ApplicationEvent;

/**
 * {@link ApplicationEvent} to be thrown when an {@link Order} has been payed.
 * 
 */
public class OrderPaidEvent extends ApplicationEvent {

	private static final long serialVersionUID = -6150362015056003378L;
	private final long orderId;

	/**
	 * Creates a new {@link OrderPaidEvent}
	 * 
	 * @param orderId the id of the order that just has been payed
	 * @param source must not be {@literal null}.
	 */
	public OrderPaidEvent(long orderId, Object source) {

		super(source);
		this.orderId = orderId;
	}

	public long getOrderId() {
		return orderId;
	}
	
}
