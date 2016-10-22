package com.idugalic.apigateway.services.reviews;

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
public class ReviewsIntegrationService {

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "stubReviews")
	public Observable<Collection<Review>> reviewsFor(final String productId, final String token) {

		// For some reason token overlay doesn't work. so the workaround ...
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		return Observable.create(new Observable.OnSubscribe<Collection<Review>>() {
			@Override
			public void call(Subscriber<? super Collection<Review>> observer) {
				try {
					if (!observer.isUnsubscribed()) {
						observer.onNext(restTemplate
								.exchange("http://reviews-service/reviews/search/findByProductId?productId={productId}",
										HttpMethod.GET, request, ReviewsResource.class, productId)
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
	private Collection<Review> stubReviews(String productId, final String token) {
		Review review = new Review();
		review.setProductId(productId);
		review.setRating(1);
		review.setName("Service not available !!!");
		review.setReview("No review");
		review.setUserName("nousername");
		return Arrays.asList(review);
	}

}
