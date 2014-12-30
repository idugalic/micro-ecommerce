package com.westum.apigateway.services.catalog;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rx.Observable;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.ObservableResult;

@Service
public class CatalogIntegrationService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    OAuth2RestOperations oauth2RestTemplate;

    @HystrixCommand(fallbackMethod = "stubProduct")
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

    private Product stubProduct(final String productId, final String token) {
        Product stub = new Product();
        stub.setId(1L);
        stub.setName("Service not available!!!");
        return stub;
    }
}
