package com.westum.apigateway.services.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.ObservableResult;

import rx.Observable;

@Service
public class CatalogIntegrationService {

	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "stubProduct")
	public Observable<Product> getProduct(final String productId, final String token) {
		return new ObservableResult<Product>() {
			@Override
			public Product invoke() {

				return restTemplate.getForObject("http://catalog-service/products/{productId}", Product.class, productId);

			}
		};
	}

	@SuppressWarnings("unused")
	private Product stubProduct(final String productId, final String token) {
		Product stub = new Product();
		stub.setId(1L);
		stub.setName("Service not available!!!");
		return stub;
	}
}
