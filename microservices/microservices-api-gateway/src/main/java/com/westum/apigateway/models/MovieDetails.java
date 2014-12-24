package com.westum.apigateway.models;

import java.util.ArrayList;
import java.util.List;

import com.westum.apigateway.services.recommendations.Movie;
import com.westum.apigateway.services.reviews.Review;

public class MovieDetails {
    private String title;
    private String mlId;
    private List<Review> reviews = new ArrayList<>();
    private List<Movie> recommendations = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMlId() {
        return mlId;
    }

    public void setMlId(String mlId) {
        this.mlId = mlId;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Movie> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Movie> recommendations) {
        this.recommendations = recommendations;
    }
}
