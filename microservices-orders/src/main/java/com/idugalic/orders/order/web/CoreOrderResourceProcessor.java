package com.idugalic.orders.order.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import com.idugalic.orders.order.domain.Order;

/**
 * {@link ResourceProcessor} implementation to add links to the {@link Order}
 * representation that indicate that the Order can be updated or cancelled as
 * long as it has not been paid yet.
 * 
 */
@Component
class CoreOrderResourceProcessor implements ResourceProcessor<Resource<Order>> {

	public static final String CANCEL_REL = "cancel";
	public static final String UPDATE_REL = "update";

	private EntityLinks entityLinks;

	@Autowired
	public CoreOrderResourceProcessor(EntityLinks entityLinks) {
		this.entityLinks = entityLinks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.hateoas.ResourceProcessor#process(org.springframework
	 * .hateoas.ResourceSupport)
	 */
	@Override
	public Resource<Order> process(Resource<Order> resource) {

		Order order = resource.getContent();

		if (!order.isPaid()) {
			resource.add(entityLinks.linkForSingleResource(order).withRel(CANCEL_REL));
			resource.add(entityLinks.linkForSingleResource(order).withRel(UPDATE_REL));
		}

		return resource;
	}
}
