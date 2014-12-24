package com.westum.recommendations.model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Movie {

    @GraphId
    private Long id;

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", mlId='" + mlId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Indexed(unique = true)
    private String mlId;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMlId() {
        return mlId;
    }

    public void setMlId(String mlId) {
        this.mlId = mlId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
