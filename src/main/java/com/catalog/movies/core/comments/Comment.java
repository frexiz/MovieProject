package com.catalog.movies.core.comments;

import com.catalog.movies.core.movies.Movie;
import com.catalog.movies.core.users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonBackReference("comments")
    @ManyToOne()
    @JoinColumn(nullable = false, name = "movie_id")
    private Movie movie;


    @JsonBackReference
    @ManyToOne()
    @JoinColumn(nullable = false, name = "user_id")
    private User author;

    @Column(name = "content", nullable = false)
    private String content;

    public Comment() {
    }
    @Column(name = "created_on")
    private Date created_on;

    public Comment(Movie movie, User author, String content) {
        this.movie = movie;
        this.author = author;
        this.content = content;
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
//        this.date = sdf.format(date);
        this.created_on = new Date();
    }

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }


    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
