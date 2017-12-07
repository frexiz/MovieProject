package com.catalog.movies.core.movies;

import com.catalog.movies.core.actors.Actor;
import com.catalog.movies.core.categories.Category;
import com.catalog.movies.core.comments.Comment;
import com.catalog.movies.core.users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private int year;

    @Column(name = "studio")
    private String studio;

    @Column(name = "description")
    private String description;

    @Column(name = "language")
    private String language;

    @Column(name = "director")
    private String director;

    @Column(name = "duration")
    private int duration;

    @Column(name = "poster", columnDefinition = "text")
    private String poster;


    @Column(name = "rating",  columnDefinition = "Decimal(10,1) default '0.0'")
    private double rating;

    @Column(name = "trailer_url")
    private String trailer_url;

    @JsonBackReference("favourites")
    @ManyToMany(mappedBy = "favourites", fetch = FetchType.EAGER)
    private Set<User> users;

    @JsonManagedReference("comments")
    @OneToMany(mappedBy = "movie")
    @org.hibernate.annotations.OrderBy(clause = "created_on DESC")
    private Set<Comment> comments;

    @JsonManagedReference("actors")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "movies_actors")
    private Set<Actor> actors;

    public void addActor(Actor actor) {
        this.actors.add(actor);
    }

    @JsonManagedReference("category")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movies_categories")
    private Set<Category> categories;


    public Movie() {
    }

    public Movie(String name, int year, String studio, String description, String language, String director, int duration, String poster, double rating, String trailer_url) {
        this.name = name;
        this.year = year;
        this.studio = studio;
        this.description = description;
        this.language = language;
        this.director = director;
        this.duration = duration;
        this.poster = poster;
        this.rating = rating;
        this.trailer_url = trailer_url;
        this.users = new HashSet<>();
        this.comments = new HashSet<>();
        this.actors = new HashSet<>();
        this.categories =  new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }


    public void addCategory(Category category) {
        this.categories.add(category);
    }


    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }
}
