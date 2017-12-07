package com.catalog.movies.core.actors;

import com.catalog.movies.core.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ActorRepository extends JpaRepository<Actor, Integer> {

    Actor findByFullName(final String fullName);
}
