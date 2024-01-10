package com.rykzee.moviedb.manager;

import com.rykzee.moviedb.entity.ActorEntity;
import com.rykzee.moviedb.entity.MovieEntity;
import com.rykzee.moviedb.model.MovieModel;
import com.rykzee.moviedb.repository.MovieDAO;
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
public class MovieManager {

    private final MovieDAO movieDAO;

    @Autowired
    public MovieManager(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Transactional(readOnly = true)
    public List<MovieModel> getAllMovies() {
        List<MovieModel> movieModels = new ArrayList<>();
        for (MovieEntity movieEntity : movieDAO.findAll()) {
            movieModels.add(new MovieModel(movieEntity.getId(), movieEntity.getName(), movieEntity.getYear(), getShallowActorFrom(movieEntity)));
        }
        return movieModels;
    }

    @Transactional(readOnly = true)
    public MovieEntity getMovieEntity(long movieId) {
        return movieDAO.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Could not find Movie with id " + movieId));
    }

    @Transactional(readOnly = true)
    public MovieModel getMovie(long movieId) {
        MovieEntity movieEntity = getMovieEntity(movieId);
        return new MovieModel(movieEntity.getId(), movieEntity.getName(), movieEntity.getYear(), getShallowActorFrom(movieEntity));
    }

    @Transactional
    public Long createMovie(ShallowMovie movie) {
        MovieEntity createdMovie = movieDAO.save(createMovieEntityFrom(movie));
        return createdMovie.getId();
    }

    private Set<ShallowActor> getShallowActorFrom(MovieEntity movieEntity) {
        Set<ShallowActor> actors = new HashSet<>();
        for (ActorEntity actorEntity : movieEntity.getActors()) {
            actors.add(new ShallowActor(actorEntity.getId(), actorEntity.getName()));
        }
        return actors;
    }

    private MovieEntity createMovieEntityFrom(ShallowMovie movie) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setName(movie.name());
        movieEntity.setYear(movie.year());
        return movieEntity;
    }
}
