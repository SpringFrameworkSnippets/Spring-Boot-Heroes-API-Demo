package com.springsamples.heroesapi.repositories;

import com.springsamples.heroesapi.repositories.entities.HeroEntity;

import java.util.UUID;

public interface HeroesRepositoryCommand {
    int update(HeroEntity entity);
    int delete(UUID id);
}
