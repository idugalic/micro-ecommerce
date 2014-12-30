package com.westum.apigateway;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.westum.apigateway.models.MovieDetails;
import com.westum.apigateway.services.catalog.CatalogIntegrationService;
import com.westum.apigateway.services.recommendations.RecommendationsIntegrationService;
import com.westum.apigateway.services.reviews.ReviewsIntegrationService;

import rx.Observable;
import rx.Observer;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableDiscoveryClient 
@EnableZuulProxy
@EnableCircuitBreaker
@EnableOAuth2Resource
@RestController
@Import(ClientConfiguration.class)
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

    @RequestMapping("/product/{productId}")
    public DeferredResult<MovieDetails> movieDetails(@PathVariable String productId) {
        Observable<MovieDetails> details = Observable.zip(
                catalogIntegrationService.getProduct(productId),
                reviewsIntegrationService.reviewsFor(productId),
                recommendationsIntegrationService.getRecommendations(productId),
                (product, reviews, recommendations) -> {
                    MovieDetails movieDetails = new MovieDetails();
                    movieDetails.setProductId(String.valueOf(product.getId()));
                    movieDetails.setName(product.getName());
                    movieDetails.setReviews(reviews);
                    movieDetails.setRecommendations(recommendations);
                    return movieDetails;
                }
        );
        return toDeferredResult(details);
    }

    public DeferredResult<MovieDetails> toDeferredResult(Observable<MovieDetails> details) {
        DeferredResult<MovieDetails> result = new DeferredResult<>();
        details.subscribe(new Observer<MovieDetails>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(MovieDetails movieDetails) {
                result.setResult(movieDetails);
            }
        });
        return result;
    }
}
