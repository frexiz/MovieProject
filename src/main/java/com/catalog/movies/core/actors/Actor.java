package com.catalog.movies.core.actors;

import com.catalog.movies.core.movies.Movie;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "actors")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @JsonBackReference("actors")
    @ManyToMany(mappedBy = "actors", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Movie> movies;

    public Actor() {
    }

    public Actor(String fullName) {
        this.fullName = fullName;
        this.movies = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
