package com.springsamples.heroesapi.repositories;

import com.springsamples.heroesapi.repositories.entities.HeroEntity;

import java.util.List;

public interface HeroesRepository {
    List<HeroEntity> findAll();
}
