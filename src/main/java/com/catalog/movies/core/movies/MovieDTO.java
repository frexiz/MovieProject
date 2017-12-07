package com.catalog.movies.core.movies;

public class MovieDTO {
    public String name;
    private int year;
    public String studio;
    private String description;
    private String language;
    private String director;
    private int duration;
    private String poster;
    private double rating;
    private String trailer_url;
    private String actors;
    private String category;

    public MovieDTO() {
    }

    public MovieDTO(String name, int year, String studio, String description, String language, String director, int duration, String poster, double rating, String trailer_url, String actors, String category) {
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
        this.actors = actors;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
