package com.rykzee.moviedb.repository;

import com.rykzee.moviedb.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDAO extends JpaRepository<MovieEntity, Long> {
}
