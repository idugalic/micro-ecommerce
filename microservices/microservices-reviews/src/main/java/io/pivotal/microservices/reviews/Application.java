package io.pivotal.microservices.reviews;

import io.pivotal.microservices.reviews.models.Review;
import io.pivotal.microservices.reviews.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@EnableMongoRepositories
@EnableEurekaClient
@RestController
public class Application implements CommandLineRunner {

    @Autowired
    ReviewRepository reviewRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        reviewRepository.deleteAll();
    }

    @RequestMapping(value = "/reviews/reviews", method = RequestMethod.GET)
    public Iterable<Review> reviews() {
        return reviewRepository.findAll();
    }

    @RequestMapping(value = "/reviews/reviews/{mlId}", method = RequestMethod.GET)
    public Iterable<Review> reviews(@PathVariable String mlId) {
        return reviewRepository.findByMlId(mlId);
    }

    @RequestMapping(value = "/reviews/reviews", method = RequestMethod.POST)
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        reviewRepository.save(review);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
}
