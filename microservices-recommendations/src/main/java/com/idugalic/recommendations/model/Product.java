package com.idugalic.recommendations.model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Product {

	@GraphId
	private Long id;

	@Override
	public String toString() {
		return "Product{" + "id=" + id + ", productId='" + productId + '\'' + ", name='" + name + '\'' + '}';
	}

	@Indexed(unique = true)
	private String productId;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
