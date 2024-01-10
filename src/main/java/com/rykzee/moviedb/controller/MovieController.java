package com.rykzee.moviedb.controller;

import com.rykzee.moviedb.manager.MovieManager;
import com.rykzee.moviedb.model.MovieModel;
import com.rykzee.moviedb.shallow.ShallowMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/moviedb/movies")
public class MovieController {

    private final MovieManager movieManager;

    @Autowired
    public MovieController(MovieManager movieManager) {
        this.movieManager = movieManager;
    }

    @GetMapping
    public ResponseEntity<List<MovieModel>> getAllMovies() {
        return new ResponseEntity<>(movieManager.getAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieModel> getMovie(@PathVariable long movieId) {
        MovieModel movie = movieManager.getMovie(movieId);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createMovie(@RequestBody ShallowMovie movie) {
        long createdMovieId = movieManager.createMovie(movie);
        return new ResponseEntity<>(createdMovieId, HttpStatus.CREATED);
    }
}
