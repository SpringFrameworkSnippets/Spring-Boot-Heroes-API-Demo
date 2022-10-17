package com.springsamples.heroesapi.repositories;

import com.springsamples.heroesapi.repositories.entities.HeroEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HeroesRepositoryQuery {
    List<HeroEntity> findAll();
    Optional<HeroEntity> findById(UUID id);
    List<HeroEntity> findByNameContains(String name);
}
