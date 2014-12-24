package com.westum.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
@RestController
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

    @RequestMapping("/movie/{mlId}")
    public DeferredResult<MovieDetails> movieDetails(@PathVariable String mlId) {
        Observable<MovieDetails> details = Observable.zip(
                catalogIntegrationService.getMovie(mlId),
                reviewsIntegrationService.reviewsFor(mlId),
                recommendationsIntegrationService.getRecommendations(mlId),
                (movie, reviews, recommendations) -> {
                    MovieDetails movieDetails = new MovieDetails();
                    movieDetails.setMlId(movie.getMlId());
                    movieDetails.setTitle(movie.getTitle());
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
