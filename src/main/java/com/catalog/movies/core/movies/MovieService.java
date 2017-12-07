package com.catalog.movies.core.movies;

import com.catalog.movies.core.categories.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by G510 on 09.3.2017 г..
 */
public interface MovieService {
    List<Movie> findAll();

    Movie findOne(final Integer id);

    Movie findByName(String name);

    void delete(Integer id);

    Page<Movie> findAllByCategoryId(final Integer categoryId, Pageable pageable);

    Page<Movie> findAll(Pageable pageable);

    Movie saveAndFlush(Movie movie);

    Page<Movie> findAllByCategories_Name(String categoryName, Pageable pageable);

    Page<Movie> findAllByActors_Name(String actorName, Pageable pageable);

    Page<Movie> findAllByUsers_Id(Integer userId, Pageable pageable);

    Integer countMoviesByUsers_Id(Integer userId);

    List<Movie> findTop6ByOrderByRatingDesc();



}
