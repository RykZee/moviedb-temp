package com.rykzee.moviedb.repository;

import com.rykzee.moviedb.entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorDAO extends JpaRepository<ActorEntity, Long> {
}
