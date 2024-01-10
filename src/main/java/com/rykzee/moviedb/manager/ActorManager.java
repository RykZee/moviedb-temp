package com.rykzee.moviedb.manager;

import com.rykzee.moviedb.entity.ActorEntity;
import com.rykzee.moviedb.entity.MovieEntity;
import com.rykzee.moviedb.model.ActorModel;
import com.rykzee.moviedb.model.MovieModel;
import com.rykzee.moviedb.repository.ActorDAO;
import com.rykzee.moviedb.shallow.ShallowActor;
import com.rykzee.moviedb.shallow.ShallowMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ActorManager {

    private final ActorDAO actorDAO;

    private final MovieManager movieManager;

    @Autowired
    public ActorManager(ActorDAO actorDAO, MovieManager movieManager) {
        this.actorDAO = actorDAO;
        this.movieManager = movieManager;
    }

    @Transactional(readOnly = true)
    public List<ActorModel> getAllActors() {
        List<ActorModel> actorModels = new ArrayList<>();
        for (ActorEntity actorEntity : actorDAO.findAll()) {
            actorModels.add(new ActorModel(actorEntity.getId(), actorEntity.getName(), getShallowMovieFrom(actorEntity)));
        }
        return actorModels;
    }

    @Transactional(readOnly = true)
    public ActorModel getActor(long actorId) {
        ActorEntity actorEntity = actorDAO.findById(actorId)
                .orElseThrow(() -> new RuntimeException("Could not find Actor with id " + actorId));
        return new ActorModel(actorEntity.getId(), actorEntity.getName(), getShallowMovieFrom(actorEntity));
    }

    @Transactional
    public Long createActor(ShallowActor actor) {
        ActorEntity createdActor = actorDAO.save(createActorEntityFrom(actor));
        return createdActor.getId();
    }

    @Transactional
    public MovieModel addActorsToMovie(long movieId, List<Long> actors) {
        MovieEntity movieEntity = movieManager.getMovieEntity(movieId);
        List<ActorEntity> actorEntities = actorDAO.findAllById(actors);
        for (ActorEntity actorEntity : actorEntities) {
            actorEntity.addMovie(movieEntity);
        }

        return new MovieModel(movieEntity.getId(), movieEntity.getName(), movieEntity.getYear(), getAllActors(movieEntity, actorEntities));
    }

    private ActorEntity createActorEntityFrom(ShallowActor actor) {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setName(actor.name());
        return actorEntity;
    }

    private Set<ShallowMovie> getShallowMovieFrom(ActorEntity actorEntity) {
        Set<ShallowMovie> movies = new HashSet<>();
        for (MovieEntity movieEntity : actorEntity.getMovies()) {
            movies.add(new ShallowMovie(movieEntity.getId(), movieEntity.getName(), movieEntity.getYear()));
        }
        return movies;
    }

    private Set<ShallowActor> getAllActors(MovieEntity movieEntity, List<ActorEntity> actorEntities) {
        Set<ShallowActor> actors = new HashSet<>();
        for (ActorEntity actor : movieEntity.getActors()) {
            actors.add(new ShallowActor(actor.getId(), actor.getName()));
        }

        for (ActorEntity actorEntity : actorEntities) {
            actors.add(new ShallowActor(actorEntity.getId(), actorEntity.getName()));
        }
        return actors;
    }
}
