package com.idugalic.apigateway.services.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import rx.Observable;
import rx.Subscriber;

@Service
public class CatalogIntegrationService {

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "stubProduct")
	public Observable<Product> getProduct(final String productId, final String token) {

		// For some reason token overlay doesn't work. so the workaround ...
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		return Observable.create(new Observable.OnSubscribe<Product>() {
			@Override
			public void call(Subscriber<? super Product> observer) {
				try {
					if (!observer.isUnsubscribed()) {

						observer.onNext(restTemplate.exchange("http://catalog-service/products/{productId}",
								HttpMethod.GET, request, Product.class, productId).getBody());
						observer.onCompleted();
					}
				} catch (Throwable e) {
					observer.onError(e);
				}

			}

		});
	}

	@SuppressWarnings("unused")
	private Product stubProduct(final String productId, final String token) {
		Product stub = new Product();
		stub.setId(1L);
		stub.setName("Service not available!!!");
		return stub;
	}
}
