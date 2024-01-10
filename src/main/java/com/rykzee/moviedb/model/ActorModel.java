package com.rykzee.moviedb.model;

import com.rykzee.moviedb.shallow.ShallowMovie;

import java.util.Set;

public record ActorModel(long id, String name, Set<ShallowMovie> movies) {
}
