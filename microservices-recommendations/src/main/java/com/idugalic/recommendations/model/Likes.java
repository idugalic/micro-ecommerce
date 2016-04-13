package com.idugalic.recommendations.model;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "LIKES")
public class Likes {

    @GraphId
    private Long id;

    @StartNode
    private Person person;

    @EndNode
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
    public String toString() {
        return "Likes{" +
                "id=" + id +
                ", person=" + person +
                ", product=" + product +
                '}';
    }
}
