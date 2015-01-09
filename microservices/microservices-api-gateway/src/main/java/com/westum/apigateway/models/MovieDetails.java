package com.westum.apigateway.models;

import java.util.ArrayList;
import java.util.List;

import com.westum.apigateway.services.recommendations.Product;
import com.westum.apigateway.services.reviews.Review;

public class MovieDetails {
    private String name;
    private String productId;
    private List<Review> reviews = new ArrayList<>();
    private List<Product> recommendations = new ArrayList<>();

  
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Product> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Product> recommendations) {
        this.recommendations = recommendations;
    }
}
