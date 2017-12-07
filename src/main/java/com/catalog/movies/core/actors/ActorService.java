package com.catalog.movies.core.actors;

import java.util.List;

public interface ActorService {
    List<Actor> findAll();

    Actor findOne(final Integer integer);

    Actor findByFinalName(final String finalName);

    Actor saveAndFlush(Actor actor);
}
