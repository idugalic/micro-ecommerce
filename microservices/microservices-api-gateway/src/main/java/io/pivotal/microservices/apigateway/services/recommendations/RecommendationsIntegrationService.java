package io.pivotal.microservices.apigateway.services.recommendations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.ObservableResult;

@Service
public class RecommendationsIntegrationService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "stubRecommendations",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
            })
    public Observable<List<Movie>> getRecommendations(final String mlId) {
        return new ObservableResult<List<Movie>>() {
            @Override
            public List<Movie> invoke() {
            	//TODO do this better please !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				PagedResources<LinkedHashMap> resources = null;
				List<Movie> result = null;

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
								"http://recommendations-service/movies/search/moviesLikedByPeopleWhoLiked?mlId={mlId}",
								HttpMethod.GET, null, PagedResources.class,
								mlId).getBody();
				result = new ArrayList<Movie>();
				for (LinkedHashMap review : resources.getContent()) {
					Movie mov = new Movie();
					mov.setMlId((String) review.get("mlId"));
					mov.setTitle((String) review.get("title"));
					result.add(mov);
				}

				return result;
            }
        };
    }

    private List<Movie> stubRecommendations(final String mlId) {
        Movie one = new Movie();
        one.setMlId("25");
        one.setMlId("A movie which doesn't exist");
        Movie two = new Movie();
        two.setMlId("26");
        two.setMlId("A movie about nothing");
        return Arrays.asList(one, two);
    }
}
