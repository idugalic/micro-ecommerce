package com.westum.apigateway.services.recommendations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
    public Observable<List<Product>> getRecommendations(final String productId, final String token) {
        return new ObservableResult<List<Product>>() {
            @Override
            public List<Product> invoke() {
            	//TODO do this better please !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				PagedResources<LinkedHashMap> resources = null;
				List<Product> result = null;

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
				
				HttpHeaders headers = new HttpHeaders();
				headers.add("Authorization",token);
				HttpEntity<String> request = new HttpEntity<String>(headers);

				resources = restTemplate
						.exchange("http://recommendations-service/products/search/productsLikedByPeopleWhoLiked?productId={productId}",
								HttpMethod.GET, request, PagedResources.class,
								productId).getBody();
				result = new ArrayList<Product>();
				for (LinkedHashMap review : resources.getContent()) {
					Product prod = new Product();
					prod.setProductId((String) review.get("productId"));
					
					prod.setName((String) review.get("name"));
					
					result.add(prod);
				}
				
				return result;
            }
        };
    }

    private List<Product> stubRecommendations(final String mlId, final String token) {
        Product one = new Product();
        one.setProductId("1");
        one.setName("No name");
        Product two = new Product();
        two.setProductId("2");
        two.setName("No name 2");
        return Arrays.asList(one, two);
    }
}
