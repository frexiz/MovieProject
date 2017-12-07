package com.catalog.movies.core.users;

import com.catalog.movies.core.comments.Comment;
import com.catalog.movies.core.movies.Movie;
import com.catalog.movies.core.roles.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles")
    private Set<Role> roles;

    @ManyToMany()
    @JoinTable(name = "users_movies")
    private Set<Movie> favourites;

    @Column(name = "photo", columnDefinition = "text")
    private String photo;

    @JsonManagedReference
    @OneToMany(mappedBy = "author")
    @org.hibernate.annotations.OrderBy(clause = "created_on DESC")
    private Set<Comment> comments;

    public User() {
    }

    public User(String email, String fullName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.roles = new HashSet<>();
        this.photo = null;
        this.comments = new HashSet<>();
    }

    public User(String email, String fullName, String password, String photo) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.roles = new HashSet<>();
        this.photo = photo;
        this.comments = new HashSet<>();
    }

    public User(String email, String fullName, String password, Set<Role> roles, String photo, Set<Comment> comments) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.roles = roles;
        this.photo = photo;
        this.comments = comments;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void addMovie(Movie movie) {
        this.favourites.add(movie);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Movie> getFavourites() {
        return favourites;
    }

    public void setFavourites(Set<Movie> favourites) {
        this.favourites = favourites;
    }

    @Transient
    public boolean isAdmin() {
        return this.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
    }

}
