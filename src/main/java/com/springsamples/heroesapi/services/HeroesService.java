package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;

import java.util.List;

public interface HeroesService {
    List<Hero> findAll();
}
