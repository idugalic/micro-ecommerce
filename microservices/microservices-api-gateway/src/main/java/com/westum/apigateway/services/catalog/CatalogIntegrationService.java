package com.westum.apigateway.services.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.ObservableResult;
import com.westum.apigateway.services.reviews.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import rx.Observable;

@Service
public class CatalogIntegrationService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "stubMovie")
    public Observable<Movie> getMovie(final String mlId) {
        return new ObservableResult<Movie>() {
            @Override
            public Movie invoke() {
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
								"http://catalog-service/movies/search/findByMlId?mlId={mlId}",
								HttpMethod.GET, null, PagedResources.class,
								mlId).getBody();
				result = new ArrayList<Movie>();
				for (LinkedHashMap review : resources.getContent()) {
					Movie mov = new Movie();
					mov.setMlId((String) review.get("mlId"));
					
					mov.setTitle((String) review.get("title"));
					
					result.add(mov);
				}
				if(result.isEmpty() || result.size()==0)
				{
					return null;
				}
				return result.get(0);
            }
        };
    }

    private Movie stubMovie(final String mlId) {
        Movie stub = new Movie();
        stub.setMlId(mlId);
        stub.setTitle("Interesting...the wrong title. Sssshhhh!");
        return stub;
    }
}
