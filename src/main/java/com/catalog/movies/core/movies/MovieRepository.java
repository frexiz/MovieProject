package com.catalog.movies.core.movies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, Integer> {
//    Page<Movie> findAllByCategory(Category category, Pageable pageable);
//
//    List<Movie> findAllByCategory(Category category);

    Page<Movie> findAll(Pageable pageable);


    List<Movie> findAll();

    Movie findByNameIgnoreCase(final String name);

    @Query(value = "SELECT * FROM movies, categories, movies_categories " +
            " WHERE movies.id = movies_categories.movies_id " +
            " AND categories.id = movies_categories.categories_id" +
            " AND categories.id =?1" +
            " ORDER BY movies.id ASC \n-- #pageable\n",
            countQuery ="SELECT count(*) FROM movies, categories, movies_categories" +
                    " WHERE movies.id = movies_categories.movies_id " +
                    " AND categories.id = movies_categories.categories_id" +
                    " AND categories.id =?1",
                    nativeQuery = true)
    Page<Movie> findAllByCategoryId(final Integer categoryId, Pageable pageable);

    Page<Movie> findAllByCategories_Id(Integer categoryId, Pageable pageable);

    Page<Movie> findAllByCategories_NameContainingIgnoreCase(String categoryName, Pageable pageable);

    Page<Movie> findAllByActors_FullNameContainingIgnoreCase(String fullName, Pageable pageable);

    Page<Movie> findAllByUsers_Id(Integer userId, Pageable pageable);

    Integer countMoviesByUsers_Id(Integer userId);

     List<Movie> findTop6ByOrderByRatingDesc();


}
