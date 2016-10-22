package com.idugalic.apigateway.services.recommendations;

import java.util.Arrays;
import java.util.Collection;

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
public class RecommendationsIntegrationService {

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "stubRecommendations")
	public Observable<Collection<Product>> getRecommendations(final String productId, final String token) {

		// For some reason token overlay doesn't work. so the workaround ...
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		return Observable.create(new Observable.OnSubscribe<Collection<Product>>() {
			@Override
			public void call(Subscriber<? super Collection<Product>> observer) {
				try {
					if (!observer.isUnsubscribed()) {
						observer.onNext(restTemplate
								.exchange(
										"http://recommendations-service/products/search/productsLikedByPeopleWhoLiked?productId={productId}",
										HttpMethod.GET, request, ProductsResource.class, productId)
								.getBody().getContent());
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}
		});
	}

	@SuppressWarnings("unused")
	private Collection<Product> stubRecommendations(final String mlId, final String token) {
		Product one = new Product();
		one.setProductId("1");
		one.setName("Fallback");
		Product two = new Product();
		two.setProductId("2");
		two.setName("Fallback 2");
		return Arrays.asList(one, two);
	}
}
