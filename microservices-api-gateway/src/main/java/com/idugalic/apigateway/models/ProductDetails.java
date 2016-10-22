package com.idugalic.apigateway.models;

import java.util.ArrayList;
import java.util.Collection;

import com.idugalic.apigateway.services.recommendations.Product;
import com.idugalic.apigateway.services.reviews.Review;

public class ProductDetails {
	private String name;
	private String productId;
	private Collection<Review> reviews = new ArrayList<>();
	private Collection<Product> recommendations = new ArrayList<>();

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

	public Collection<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Collection<Review> reviews2) {
		this.reviews = reviews2;
	}

	public Collection<Product> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(Collection<Product> recommendations) {
		this.recommendations = recommendations;
	}
}
