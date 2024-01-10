package com.rykzee.moviedb.controller;

import com.rykzee.moviedb.manager.ActorManager;
import com.rykzee.moviedb.model.ActorModel;
import com.rykzee.moviedb.model.MovieModel;
import com.rykzee.moviedb.shallow.ShallowActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/moviedb/actors")
public class ActorController {

    private final ActorManager actorManager;

    @Autowired
    public ActorController(ActorManager actorManager) {
        this.actorManager = actorManager;
    }

    @GetMapping
    public ResponseEntity<List<ActorModel>> getAllActors() {
        return new ResponseEntity<>(actorManager.getAllActors(), HttpStatus.OK);
    }

    @GetMapping("/{actorId}")
    public ResponseEntity<ActorModel> getActor(@PathVariable long actorId) {
        ActorModel actor = actorManager.getActor(actorId);
        return new ResponseEntity<>(actor, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createActor(@RequestBody ShallowActor actor) {
        long createdActorId = actorManager.createActor(actor);
        return new ResponseEntity<>(createdActorId, HttpStatus.CREATED);
    }

    @PutMapping("/addActorsToMovie/{movieId}")
    public ResponseEntity<MovieModel> addActorsToMovie(
            @PathVariable long movieId,
            @RequestBody List<Long> actors) {
        MovieModel movie = actorManager.addActorsToMovie(movieId, actors);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }
}
