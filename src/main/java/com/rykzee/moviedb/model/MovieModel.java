package com.rykzee.moviedb.model;

import com.rykzee.moviedb.shallow.ShallowActor;

import java.util.Set;

public record MovieModel(long id, String name, int year, Set<ShallowActor> actors) {
}
