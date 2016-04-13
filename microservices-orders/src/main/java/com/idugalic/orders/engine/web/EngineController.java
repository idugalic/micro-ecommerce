package com.idugalic.orders.engine.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idugalic.orders.engine.InProgressAware;
import com.idugalic.orders.order.domain.Order;


/**
 * Custom controller to show how to expose a custom resource into the root resource exposed by Spring Data REST.
 * 
 */
@Controller
class EngineController implements ResourceProcessor<RepositoryLinksResource> {

	public static final String ENGINE_REL = "engine";

	private InProgressAware processor;
	
	@Autowired
	public EngineController(InProgressAware processor) {
		this.processor = processor;
	}

	/**
	 * Exposes all {@link Order}s currently in preparation.
	 * 
	 * @return
	 */
	@RequestMapping("/engine")
	HttpEntity<Resources<Resource<Order>>> showOrdersInProgress() {

		Resources<Resource<Order>> orderResources = Resources.wrap(processor.getOrders());
		orderResources.add(linkTo(methodOn(EngineController.class).showOrdersInProgress()).withSelfRel());

		return new ResponseEntity<>(orderResources, HttpStatus.OK);
	}

	/**
	 * Exposes the {@link EngineController} to the root resource exposed by Spring Data REST.
	 * 
	 * @see org.springframework.hateoas.ResourceProcessor#process(org.springframework.hateoas.ResourceSupport)
	 */
	@Override
	public RepositoryLinksResource process(RepositoryLinksResource resource) {

		resource.add(linkTo(methodOn(EngineController.class).showOrdersInProgress()).withRel(ENGINE_REL));

		return resource;
	}
}
