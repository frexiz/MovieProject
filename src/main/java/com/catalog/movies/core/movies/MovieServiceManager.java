package com.catalog.movies.core.movies;

import com.catalog.movies.core.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MovieServiceManager implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public Movie findOne(Integer id) {
        return movieRepository.findOne(id);
    }

    @Override
    public Movie findByName(String name) {
        return movieRepository.findByNameIgnoreCase(name);
    }

    @Override
    public void delete(Integer id) {
        movieRepository.delete(id);
    }

    @Override
    public Page<Movie> findAllByCategoryId(Integer categoryId, Pageable pageable) {
        return movieRepository.findAllByCategories_Id(categoryId, pageable);
    }

    @Override
    public Page<Movie> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public Movie saveAndFlush(Movie movie) {
        return movieRepository.saveAndFlush(movie);
    }

    @Override
    public Page<Movie> findAllByCategories_Name(String categoryName, Pageable pageable) {
        return movieRepository.findAllByCategories_NameContainingIgnoreCase(categoryName, pageable);
    }

    @Override
    public Page<Movie> findAllByActors_Name(String actorName, Pageable pageable) {
        return this.movieRepository.findAllByActors_FullNameContainingIgnoreCase(actorName, pageable);
    }

    @Override
    public Page<Movie> findAllByUsers_Id(Integer userId, Pageable pageable) {
        return this.movieRepository.findAllByUsers_Id(userId, pageable);
    }

    @Override
    public Integer countMoviesByUsers_Id(Integer userId) {
        return this.movieRepository.countMoviesByUsers_Id(userId);
    }

    @Override
    public List<Movie> findTop6ByOrderByRatingDesc() {
        return  this.movieRepository.findTop6ByOrderByRatingDesc();
    }


}
