package com.springsamples.heroesapi.services;

import com.springsamples.heroesapi.domain.Hero;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HeroesServiceQuery {
    List<Hero> findAll();
    Optional<Hero> findById(UUID id);
    List<Hero> findByNameContains(String name);
}
