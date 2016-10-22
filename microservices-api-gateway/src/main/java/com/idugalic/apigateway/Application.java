package com.idugalic.apigateway;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import com.idugalic.apigateway.models.ProductDetails;
import com.idugalic.apigateway.services.catalog.CatalogIntegrationService;
import com.idugalic.apigateway.services.recommendations.RecommendationsIntegrationService;
import com.idugalic.apigateway.services.reviews.ReviewsIntegrationService;

import rx.Observable;
import rx.Observer;

@SpringBootApplication
@EnableZuulProxy
@EnableHystrix
@RestController
@EnableOAuth2Sso
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	CatalogIntegrationService catalogIntegrationService;

	@Autowired
	ReviewsIntegrationService reviewsIntegrationService;

	@Autowired
	RecommendationsIntegrationService recommendationsIntegrationService;

	@RequestMapping("/")
	public HttpEntity<RootResource> getRootResource() {
		RootResource resource = new RootResource();
		String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();

		Link catalogServiceLink = new Link(new UriTemplate(baseUri + "/catalog"), RootResource.LINK_NAME_CATALOG);
		resource.add(catalogServiceLink);

		Link reviewServiceLink = new Link(new UriTemplate(baseUri + "/reviews"), RootResource.LINK_NAME_REVIEW);
		resource.add(reviewServiceLink);

		Link recommendationServiceLink = new Link(new UriTemplate(baseUri + "/recommendations"),
				RootResource.LINK_NAME_RECOMMENDATIONS);
		resource.add(recommendationServiceLink);

		Link orderServiceLink = new Link(new UriTemplate(baseUri + "/orders"), RootResource.LINK_NAME_ORDERS);
		resource.add(orderServiceLink);

		Link productLink = new Link(new UriTemplate(baseUri + "/product/{id}"), RootResource.LINK_NAME_PRODUCT_DETAIL);
		resource.add(productLink);

		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	@RequestMapping("/product/{productId}")
	public DeferredResult<ProductDetails> productDetails(@PathVariable String productId, HttpServletRequest request) {
		String token = request.getHeader("authorization");
		Observable<ProductDetails> details = Observable.zip(catalogIntegrationService.getProduct(productId, token),
				reviewsIntegrationService.reviewsFor(productId, token),
				recommendationsIntegrationService.getRecommendations(productId, token),
				(product, reviews, recommendations) -> {
					ProductDetails productDetails = new ProductDetails();
					productDetails.setProductId(String.valueOf(product.getId()));
					productDetails.setName(product.getName());
					productDetails.setReviews(reviews);
					productDetails.setRecommendations(recommendations);
					return productDetails;
				});
		return toDeferredResult(details);
	}

	public DeferredResult<ProductDetails> toDeferredResult(Observable<ProductDetails> details) {
		DeferredResult<ProductDetails> result = new DeferredResult<>();
		details.subscribe(new Observer<ProductDetails>() {
			@Override
			public void onCompleted() {
			}

			@Override
			public void onError(Throwable throwable) {
			}

			@Override
			public void onNext(ProductDetails productDetails) {
				result.setResult(productDetails);
			}
		});
		return result;
	}

	@Configuration
	@EnableResourceServer
	static class ResourceServer extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.requestMatchers().and().authorizeRequests().antMatchers(HttpMethod.GET, "/reviews/**").authenticated()
					.antMatchers(HttpMethod.GET, "/catalog/**").authenticated()
					.antMatchers(HttpMethod.GET, "/recommendations/**").authenticated()
					.antMatchers(HttpMethod.GET, "/orders/**").authenticated()
					.antMatchers(HttpMethod.GET, "/product/**").authenticated()

					.antMatchers("/metrics/**").access("#oauth2.hasScope('metrics')").antMatchers("/trace/**")
					.access("#oauth2.hasScope('trace')").antMatchers("/dump/**").access("#oauth2.hasScope('dump')")
					.antMatchers("/shutdown/**").access("#oauth2.hasScope('shutdown')").antMatchers("/beans/**")
					.access("#oauth2.hasScope('beans')").antMatchers("/autoconfig/**")
					.access("#oauth2.hasScope('autoconfig')").antMatchers("/configprops/**")
					.access("#oauth2.hasScope('configprops')").antMatchers("/env/**").access("#oauth2.hasScope('env')")
					.antMatchers("/mappings/**").access("#oauth2.hasScope('mappings')");

		}
	}

}
