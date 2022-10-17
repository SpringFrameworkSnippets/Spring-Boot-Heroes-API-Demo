package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;
import com.springsamples.heroesapi.web.model.HeroDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HeroesService {
    List<Hero> findAll();
    Optional<Hero> findById(UUID id);
    List<Hero> findByNameContains(String name);
    void updateHero(HeroDto dto);
}
