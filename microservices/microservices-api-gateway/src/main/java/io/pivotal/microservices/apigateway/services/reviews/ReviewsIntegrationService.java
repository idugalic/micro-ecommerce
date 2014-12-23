package io.pivotal.microservices.apigateway.services.reviews;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rx.Observable;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.ObservableResult;

@Service
public class ReviewsIntegrationService {

	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "stubReviews")
	public Observable<List<Review>> reviewsFor(String mlId) {
		return new ObservableResult<List<Review>>() {
			@Override
			public List<Review> invoke() {
//TODO do this better please !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				PagedResources<LinkedHashMap> resources = null;
				List<Review> result = null;

				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(
						DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
				mapper.registerModule(new Jackson2HalModule());
				MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
				converter.setSupportedMediaTypes(MediaType
						.parseMediaTypes("application/hal+json"));
				converter.setObjectMapper(mapper);
				restTemplate.setMessageConverters(Collections
						.<HttpMessageConverter<?>> singletonList(converter));

				resources = restTemplate
						.exchange(
								"http://reviews-service/reviews/search/findByMlId?mlId={mlId}",
								HttpMethod.GET, null, PagedResources.class,
								mlId).getBody();
				result = new ArrayList<Review>();
				for (LinkedHashMap review : resources.getContent()) {
					Review rev = new Review();
					rev.setMlId((String) review.get("mlId"));
					rev.setRating((int) review.get("rating"));
					rev.setReview((String) review.get("review"));
					rev.setTitle((String) review.get("title"));
					rev.setUserName((String) review.get("userName"));
					result.add(rev);
				}

				return result;
			}
		};
	}

	private List<Review> stubReviews(String mlId) {
		Review review = new Review();
		review.setMlId(mlId);
		review.setRating(4);
		review.setTitle("Interesting...the wrong title. Sssshhhh!");
		review.setReview("Awesome sauce!");
		review.setUserName("joeblow");
		return Arrays.asList(review);
	}

}
