package com.westum.apigateway;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.security.oauth2.client.ClientConfiguration;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.cloud.security.oauth2.sso.OAuth2SsoConfigurerAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import rx.Observable;
import rx.Observer;

import com.westum.apigateway.models.ProductDetails;
import com.westum.apigateway.services.catalog.CatalogIntegrationService;
import com.westum.apigateway.services.recommendations.RecommendationsIntegrationService;
import com.westum.apigateway.services.reviews.ReviewsIntegrationService;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableDiscoveryClient 
@EnableZuulProxy
@EnableCircuitBreaker
@RestController
@Import(ClientConfiguration.class)
@EnableHypermediaSupport(type = { HypermediaType.HAL })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Autowired
    CatalogIntegrationService catalogIntegrationService;

    @Autowired
    ReviewsIntegrationService reviewsIntegrationService;

    @Autowired
    RecommendationsIntegrationService recommendationsIntegrationService;
   
    @Configuration
    protected static class TestConfiguration extends OAuth2SsoConfigurerAdapter {
        @Override
        public void match(RequestMatchers matchers) {
            matchers.antMatchers("/**");
        }
    }
    @RequestMapping("/")
    public HttpEntity<RootResource> getRootResource() {
    	 RootResource resource = new RootResource();
    	 String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
         
         Link catalogServiceLink = new Link(
                 new UriTemplate(baseUri + "/catalog"), RootResource.LINK_NAME_CATALOG);
         resource.add(catalogServiceLink);
         
         Link reviewServiceLink = new Link(
                 new UriTemplate(baseUri + "/reviews"), RootResource.LINK_NAME_REVIEW);
         resource.add(reviewServiceLink);
         
         Link recommendationServiceLink = new Link(
                 new UriTemplate(baseUri + "/recommendations"), RootResource.LINK_NAME_RECOMMENDATIONS);
         resource.add(recommendationServiceLink);
         
         Link orderServiceLink = new Link(
                 new UriTemplate(baseUri + "/orders"), RootResource.LINK_NAME_ORDERS);
         resource.add(orderServiceLink);
         
         Link productLink = new Link(
                 new UriTemplate(baseUri + "/product/{id}"), RootResource.LINK_NAME_PRODUCT_DETAIL);
         resource.add(productLink);
         
         
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping("/product/{productId}")
    public DeferredResult<ProductDetails> productDetails(@PathVariable String productId , HttpServletRequest request) {
    	String token = request.getHeader("authorization");
        Observable<ProductDetails> details = Observable.zip(
                catalogIntegrationService.getProduct(productId, token),
                reviewsIntegrationService.reviewsFor(productId, token),
                recommendationsIntegrationService.getRecommendations(productId, token),
                (product, reviews, recommendations) -> {
                    ProductDetails productDetails = new ProductDetails();
                    productDetails.setProductId(String.valueOf(product.getId()));
                    productDetails.setName(product.getName());
                    productDetails.setReviews(reviews);
                    productDetails.setRecommendations(recommendations);
                    return productDetails;
                }
        );
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
	@EnableOAuth2Resource
//	@EnableHypermediaSupport(type = { HypermediaType.HAL })
	static class ResourceServer extends ResourceServerConfigurerAdapter {

	  @Override
	  public void configure(HttpSecurity http) throws Exception {
	    http.requestMatchers().and().authorizeRequests()
	     .antMatchers(HttpMethod.GET, "/reviews/**").authenticated()
	     .antMatchers(HttpMethod.GET, "/catalog/**").authenticated()
	     .antMatchers(HttpMethod.GET, "/recommendations/**").authenticated()
	     .antMatchers(HttpMethod.GET, "/orders/**").authenticated()
	     .antMatchers(HttpMethod.GET, "/product/**").authenticated()
	     

	     .antMatchers("/metrics/**").access("#oauth2.hasScope('metrics')")
         .antMatchers("/trace/**").access("#oauth2.hasScope('trace')")
         .antMatchers("/dump/**").access("#oauth2.hasScope('dump')")
         .antMatchers("/shutdown/**").access("#oauth2.hasScope('shutdown')")
         .antMatchers("/beans/**").access("#oauth2.hasScope('beans')")
         .antMatchers("/autoconfig/**").access("#oauth2.hasScope('autoconfig')")
		 .antMatchers("/configprops/**").access("#oauth2.hasScope('configprops')")
		 .antMatchers("/env/**").access("#oauth2.hasScope('env')")
		 .antMatchers("/mappings/**").access("#oauth2.hasScope('mappings')");
	     
	  }
    }

}
