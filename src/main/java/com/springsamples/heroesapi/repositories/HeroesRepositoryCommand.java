package com.springsamples.heroesapi.repositories;

import com.springsamples.heroesapi.repositories.entities.HeroEntity;

public interface HeroesRepositoryCommand {
    int update(HeroEntity entity);
}
