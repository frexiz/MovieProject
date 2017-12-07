package com.catalog.movies.core.categories;

import com.catalog.movies.core.movies.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findOne(final Integer integer);

    Category findByName(final String name);

    Category saveAndFlush(Category category);
}
