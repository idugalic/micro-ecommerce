package com.idugalic.apigateway.services.catalog;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.ObservableResult;

import rx.Observable;

@Service
public class CatalogIntegrationService {

	@Autowired
	@Qualifier("loadBalancedRestTemplate")
    @LoadBalanced
    RestTemplate restTemplate;
	

	@HystrixCommand(fallbackMethod = "stubProduct",commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
    })
	  public Observable<Product> getProduct(final String productId, final String token) {
        return new ObservableResult<Product>() {
            @Override
            public Product invoke() {
            
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
				ResponseEntity<Product> response = restTemplate.exchange("http://catalog-service/products/{productId}", HttpMethod.GET, request, Product.class, productId);
				Product resutl = response.getBody();
				//Product resutl = restTemplate.getForObject("http://catalog-service/products/{productId}", Product.class, productId);
				
				return resutl;
				
				
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
