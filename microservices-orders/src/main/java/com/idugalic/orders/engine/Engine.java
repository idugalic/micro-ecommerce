package com.idugalic.orders.engine;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.idugalic.orders.order.domain.Order;
import com.idugalic.orders.order.repository.OrderRepository;
import com.idugalic.orders.payment.event.OrderPaidEvent;


/**
 * Simple {@link ApplicationListener} implementation that listens to {@link OrderPaidEvent}s marking the according
 * {@link Order} as in process, sleeping for 10 seconds and marking the order as processed right after that.
 * 
 */
@Service
class Engine implements ApplicationListener<OrderPaidEvent>, InProgressAware {

	private final OrderRepository repository;
	private final  Set<Order> ordersInProgress;
	
	@Autowired
	public Engine(OrderRepository repository) {
		this.repository = repository;
		this.ordersInProgress = Collections.newSetFromMap(new ConcurrentHashMap<Order, Boolean>());
	}

	/*
	 * (non-Javadoc)
	 * @see org.springsource.restbucks.engine.InProgressAware#getOrders()
	 */
	@Override
	public Set<Order> getOrders() {
		return ordersInProgress;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Async
	@Override
	public void onApplicationEvent(OrderPaidEvent event) {

		Order order = repository.findOne(event.getOrderId());
		order.markInPreparation();
		order = repository.save(order);

		ordersInProgress.add(order);


		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		order.markPrepared();
		repository.save(order);

		ordersInProgress.remove(order);

	}
}
