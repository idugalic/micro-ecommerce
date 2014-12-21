package io.pivotal.microservices.catalog.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String mlId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movies_genres",
            joinColumns = {@JoinColumn(name="movie_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="genre_id", referencedColumnName = "id")})
    private List<Genre> genres;

    @Column(nullable = false)
    private int numberInStock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getMlId() {
        return mlId;
    }

    public void setMlId(String mlId) {
        this.mlId = mlId;
    }

    public int getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(int numberInStock) {
        this.numberInStock = numberInStock;
    }
}
