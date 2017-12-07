package com.catalog.movies.core.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorServiceManager implements ActorService{
    @Autowired
    ActorRepository actorRepository;

    @Override
    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

    @Override
    public Actor findOne(Integer integer) {
        return actorRepository.findOne(integer);
    }

    @Override
    public Actor findByFinalName(String finalName) {
        return actorRepository.findByFullName(finalName);
    }

    @Override
    public Actor saveAndFlush(Actor actor) {
        return actorRepository.saveAndFlush(actor);
    }
}
